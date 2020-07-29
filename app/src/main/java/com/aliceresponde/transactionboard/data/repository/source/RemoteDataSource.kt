package com.aliceresponde.transactionboard.data.repository.source

import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.TransactionResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse

interface RemoteDataSource {
    suspend fun getTransactions(): List<TransactionResponse>
    suspend fun getUserById(id: Int): UserResponse
    suspend fun getTransactionInfo(id: Int): TransactionInfoResponse
    suspend fun getUserInfo(id: Int): UserResponse
}
