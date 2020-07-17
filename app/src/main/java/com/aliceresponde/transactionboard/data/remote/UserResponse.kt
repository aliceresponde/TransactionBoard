package com.aliceresponde.transactionboard.data.remote

data class UserResponse(
    val id: Int,
    val createdDate: String,
    val name: String,
    val birthday: String,
    val photo: String
)