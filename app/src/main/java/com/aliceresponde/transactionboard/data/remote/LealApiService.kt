package com.aliceresponde.transactionboard.data.remote

import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.TransactionsResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface LealApiService {
    companion object {
        private const val BASE_URL = "https:://mobiletest.leal.co/"

        operator fun invoke(interceptor: Interceptor): LealApiService {
            val logInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LealApiService::class.java)
        }
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