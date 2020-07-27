package com.aliceresponde.transactionboard.data.remote

import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.TransactionsResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource
import javax.inject.Inject

class RetrofitDataSource @Inject constructor (private val service: LealApiService) : RemoteDataSource {

    override suspend fun getTransactions(): TransactionsResponse = service.getTransactions()

    override suspend fun getUserById(id: Int): UserResponse = service.getUserById(id)

    override suspend fun getTransactionInfo(id: Int): TransactionInfoResponse =
        service.getTransactionInfo(id)
}