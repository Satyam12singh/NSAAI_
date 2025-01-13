package com.example.nsaai.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    // Use val with only a getter for isAuthenticated
    val isAuthenticated: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        // Email is verified
                        _authState.value = AuthState.Success
                    } else {
                        // Email is not verified
                        auth.signOut() // Log out the user
                        _authState.value = AuthState.Error("Please verify your email before logging in.")
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }


    fun signup(email: String, password: String) {
        _authState.value = AuthState.Loading
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                    auth.signOut()
                    _authState.value = AuthState.Error("A verification email has been sent. Please verify before logging in.")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
