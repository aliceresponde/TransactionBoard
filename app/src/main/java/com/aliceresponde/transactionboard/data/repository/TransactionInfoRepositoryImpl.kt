package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource

class TransactionInfoRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    TransactionInfoRepository {
    override suspend fun getTransactionInfo(id: Int): TransactionInfoResponse {
        return remoteDataSource.getTransactionInfo(id)
    }
}