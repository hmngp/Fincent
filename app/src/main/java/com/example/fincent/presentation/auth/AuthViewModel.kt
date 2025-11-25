package com.example.fincent.presentation.auth

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
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth: StateFlow<Boolean> = _isCheckingAuth.asStateFlow()

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
            _isCheckingAuth.value = false
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
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
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

