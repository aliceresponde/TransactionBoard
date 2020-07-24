package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions() : List <Transaction>
}