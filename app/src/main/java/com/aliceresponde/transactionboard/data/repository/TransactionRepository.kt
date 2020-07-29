package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse

interface TransactionRepository {
    suspend fun getTransactions(): List<TransactionEntity>
    suspend fun deleteAllTransactions()
    suspend fun updateTransaction(transaction: TransactionEntity)
    suspend fun restoreData(): List<TransactionEntity>

    suspend fun getUserInfo(id: Int): UserResponse

    suspend fun getTransactionInfo(id: Int): TransactionInfoResponse
}