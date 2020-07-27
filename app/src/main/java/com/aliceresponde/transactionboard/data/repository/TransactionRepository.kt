package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.local.TransactionEntity

interface TransactionRepository {
    suspend fun getTransactions(): List<TransactionEntity>
    suspend fun deleteAllTransactions()
    suspend fun updateTransaction(transaction: TransactionEntity)
    suspend fun restoreData(): List<TransactionEntity>
}