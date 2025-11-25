package com.example.fincent.data.repository

import com.example.fincent.domain.model.User
import com.example.fincent.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    val currentUser: FirebaseUser? get() = auth.currentUser

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun signUp(email: String, password: String, displayName: String): Resource<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Resource.Error("User creation failed")

            // Update profile
            firebaseUser.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
            ).await()

            // Send verification email
            firebaseUser.sendEmailVerification().await()

            // Create user document in Firestore
            val user = User(
                uid = firebaseUser.uid,
                email = email,
                displayName = displayName,
                isEmailVerified = false,
                createdAt = System.currentTimeMillis()
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(user)
                .await()

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Sign up failed")
        }
    }

    suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Resource.Error("Sign in failed")

            // Check email verification
            firebaseUser.reload().await()

            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = userDoc.toObject(User::class.java) ?: User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = firebaseUser.displayName ?: "",
                isEmailVerified = firebaseUser.isEmailVerified
            )

            Resource.Success(user.copy(isEmailVerified = firebaseUser.isEmailVerified))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Sign in failed")
        }
    }

    suspend fun sendVerificationEmail(): Resource<Unit> {
        return try {
            val user = currentUser ?: return Resource.Error("No user logged in")
            user.sendEmailVerification().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to send verification email")
        }
    }

    suspend fun resetPassword(email: String): Resource<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Password reset failed")
        }
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun updateUserProfile(user: User): Resource<Unit> {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Profile update failed")
        }
    }

    fun getUserProfile(uid: String): Flow<Resource<User>> = callbackFlow {
        val subscription = firestore.collection("users")
            .document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to get user profile"))
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java)
                    if (user != null) {
                        trySend(Resource.Success(user))
                    }
                }
            }

        awaitClose { subscription.remove() }
    }

    suspend fun deleteAccount(): Resource<Unit> {
        return try {
            val user = currentUser ?: return Resource.Error("No user logged in")
            
            // Delete user document
            firestore.collection("users")
                .document(user.uid)
                .delete()
                .await()

            // Delete Firebase Auth user
            user.delete().await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Account deletion failed")
        }
    }
}

