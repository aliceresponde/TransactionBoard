package com.aliceresponde.transactionboard.data.remote

data class TransactionInfoResponse(
    val transactionId: Int,
    val value: Int,
    val points: Int
)
