package com.aliceresponde.transactionboard.data.repository.source

import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.TransactionsResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse

interface RemoteDataSource {
    suspend fun getTransactions(): TransactionsResponse
    suspend fun getUserById(id: Int): UserResponse
    suspend fun getTransactionInfo(id: Int): TransactionInfoResponse
}
