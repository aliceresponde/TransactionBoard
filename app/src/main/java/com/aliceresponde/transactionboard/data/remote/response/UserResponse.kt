package com.aliceresponde.transactionboard.data.remote.response

data class UserResponse(
    val id: Int,
    val createdDate: String,
    val name: String,
    val birthday: String,
    val photo: String
)