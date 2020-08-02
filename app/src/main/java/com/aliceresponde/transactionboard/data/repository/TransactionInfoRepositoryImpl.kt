package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.Resource
import com.aliceresponde.transactionboard.data.SuccessResource
import com.aliceresponde.transactionboard.data.remote.NoInternetException
import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource

class TransactionInfoRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    TransactionInfoRepository {
    override suspend fun getTransactionInfo(id: Int): Resource<TransactionInfoResponse> {
        return try {
            val result = remoteDataSource.getTransactionInfo(id)
            SuccessResource(result)
        } catch (e: NoInternetException) {
            ErrorResource(Resource.INTERNET_ERROR_MESSAGE)
        }
    }
}