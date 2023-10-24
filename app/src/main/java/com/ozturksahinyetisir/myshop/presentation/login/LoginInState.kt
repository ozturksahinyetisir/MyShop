package com.ozturksahinyetisir.myshop.presentation.login

data class LoginInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)