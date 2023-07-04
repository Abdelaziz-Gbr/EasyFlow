package com.easyflow.network.models

data class UpdatePasswordModel(
    val oldPassword: String,
    val resetPassword: ResetPasswordModel
)
