package com.example.nsaai.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    private val _authState = mutableStateOf<AuthenticationState>(AuthenticationState.Idle)
    val authState: State<AuthenticationState> = _authState

    fun signup(email: String, password: String) {
        _authState.value = AuthenticationState.Loading
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthenticationState.Success
                } else {
                    _authState.value = AuthenticationState.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }
}

sealed class AuthenticationState {
    object Idle : AuthenticationState()
    object Loading : AuthenticationState()
    object Success : AuthenticationState()
    data class Error(val message: String) : AuthenticationState()
}
