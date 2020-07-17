package com.aliceresponde.transactionboard.data.remote

import com.aliceresponde.transactionboard.data.remote.response.TransactionsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LealApiService {
    companion object {
        const val  BASE_URL = "https:://mobiletest.leal.co/"
    }

    // https://mobiletest.leal.co/transactions
    @GET("transactions")
    suspend fun getTransactions(): TransactionsResponse

    // https://mobiletest.leal.co/users/1
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): UserResponse


    //"https://mobiletest.leal.co/transactions/1/info"
    @GET("transactions/{id}/info")
    suspend fun getTransactionInfo(@Path("id") transactionId: Int): TransactionInfoResponse
}