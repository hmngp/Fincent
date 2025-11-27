package com.example.fincent.presentation.auth

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincent.data.repository.AuthRepository
import com.example.fincent.domain.model.User
import com.example.fincent.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth: StateFlow<Boolean> = _isCheckingAuth.asStateFlow()

    companion object {
        val KEY_NAME = androidx.datastore.preferences.core.stringPreferencesKey("user_name")
        val KEY_EMAIL = androidx.datastore.preferences.core.stringPreferencesKey("user_email")
        val KEY_UNIVERSITY = androidx.datastore.preferences.core.stringPreferencesKey("user_university")
        val KEY_IS_VERIFIED = androidx.datastore.preferences.core.booleanPreferencesKey("user_is_verified")
        val KEY_IS_PROFILE_SETUP = androidx.datastore.preferences.core.booleanPreferencesKey("user_is_profile_setup")
    }

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        val firebaseUser = authRepository.currentUser
        if (firebaseUser != null) {
            viewModelScope.launch {
                _isCheckingAuth.value = true
                authRepository.getUserProfile(firebaseUser.uid).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _currentUser.value = resource.data
                            _isCheckingAuth.value = false
                        }
                        is Resource.Error -> {
                            _authState.value = AuthState.Error(resource.message)
                            _isCheckingAuth.value = false
                        }
                        is Resource.Loading -> {}
                    }
                }
            }
        } else {
            // Load local profile if no Firebase user
            loadLocalProfile()
        }
    }

    private fun loadLocalProfile() {
        viewModelScope.launch {
            _isCheckingAuth.value = true
            dataStore.data.collect { preferences ->
                val isSetup = preferences[KEY_IS_PROFILE_SETUP] ?: false
                if (isSetup) {
                    val user = User(
                        uid = "demo-user",
                        displayName = preferences[KEY_NAME] ?: "",
                        email = preferences[KEY_EMAIL] ?: "",
                        university = preferences[KEY_UNIVERSITY] ?: "",
                        isEmailVerified = preferences[KEY_IS_VERIFIED] ?: false
                    )
                    _currentUser.value = user
                } else {
                    _currentUser.value = null
                }
                _isCheckingAuth.value = false
            }
        }
    }

    fun saveLocalProfile(name: String, university: String, email: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_NAME] = name
                preferences[KEY_UNIVERSITY] = university
                preferences[KEY_EMAIL] = email
                preferences[KEY_IS_PROFILE_SETUP] = true
                preferences[KEY_IS_VERIFIED] = false // Reset verification on new profile
            }
            // Update current user state immediately
            _currentUser.value = User(
                uid = "demo-user",
                displayName = name,
                email = email,
                university = university,
                isEmailVerified = false
            )
        }
    }

    fun verifyLocalEmail() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_IS_VERIFIED] = true
            }
            _currentUser.value = _currentUser.value?.copy(isEmailVerified = true)
        }
    }

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authRepository.signUp(email, password, displayName)) {
                is Resource.Success -> {
                    _currentUser.value = result.data
                    _authState.value = AuthState.Success("Please verify your email")
                }
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
                is Resource.Loading -> {}
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authRepository.signIn(email, password)) {
                is Resource.Success -> {
                    _currentUser.value = result.data
                    _authState.value = if (result.data.isEmailVerified) {
                        AuthState.Authenticated
                    } else {
                        AuthState.EmailNotVerified
                    }
                }
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
                is Resource.Loading -> {}
            }
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            when (val result = authRepository.sendVerificationEmail()) {
                is Resource.Success -> _authState.value = AuthState.Success("Verification email sent")
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
                is Resource.Loading -> {}
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authRepository.resetPassword(email)) {
                is Resource.Success -> _authState.value = AuthState.Success("Password reset email sent")
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
                is Resource.Loading -> {}
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        viewModelScope.launch {
            dataStore.edit { it.clear() }
        }
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            if (user.uid == "demo-user") {
                saveLocalProfile(user.displayName, user.university, user.email)
                _authState.value = AuthState.Success("Profile updated")
            } else {
                _authState.value = AuthState.Loading
                when (val result = authRepository.updateUserProfile(user)) {
                    is Resource.Success -> {
                        _currentUser.value = user
                        _authState.value = AuthState.Success("Profile updated")
                    }
                    is Resource.Error -> _authState.value = AuthState.Error(result.message)
                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object EmailNotVerified : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

