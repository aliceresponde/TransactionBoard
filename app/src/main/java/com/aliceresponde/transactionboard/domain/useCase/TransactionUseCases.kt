package com.aliceresponde.transactionboard.domain.useCase

import com.aliceresponde.transactionboard.domain.Transaction

interface TransactionUseCases {
    suspend fun getTransactions() : List <Transaction>
}
