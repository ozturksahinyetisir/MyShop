package com.ozturksahinyetisir.myshop.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _state = MutableStateFlow(LoginInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: LoginInResult){
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )}
    }
    fun resetState(){
        _state.update { LoginInState()}
    }

}