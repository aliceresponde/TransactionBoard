package com.aliceresponde.transactionboard.domain.useCase

data class TransactionInfo(
    val transactionId: Int,
    val value: Int,
    val points: Int
)