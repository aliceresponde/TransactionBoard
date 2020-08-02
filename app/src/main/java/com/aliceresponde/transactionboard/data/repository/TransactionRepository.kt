package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.Resource
import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse

interface TransactionRepository {
    suspend fun getTransactions(): Resource<List<TransactionEntity>>
    suspend fun deleteAllTransactions()
    suspend fun deleteTransaction(transaction: TransactionEntity): Resource<List<TransactionEntity>>
    suspend fun updateTransaction(transaction: TransactionEntity)

    suspend fun restoreData(): Resource<List<TransactionEntity>>

    suspend fun getUserInfo(id: Int): Resource<UserResponse>
    suspend fun getTransactionInfo(id: Int): Resource<TransactionInfoResponse>
}