package com.example.nsaai.Authentication

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val authMethod: AuthMethod?= null
)

enum class AuthMethod{
    GOOGLE,FACEBOOK,EMAIL
}