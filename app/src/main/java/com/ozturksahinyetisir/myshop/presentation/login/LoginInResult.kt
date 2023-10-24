package com.ozturksahinyetisir.myshop.presentation.login

data class LoginInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
)