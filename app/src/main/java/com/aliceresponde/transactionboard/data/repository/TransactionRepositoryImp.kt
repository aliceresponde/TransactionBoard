package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.remote.NoInternetException
import com.aliceresponde.transactionboard.data.remote.response.TransactionResponse
import com.aliceresponde.transactionboard.data.repository.source.LocalDataSource
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource
import javax.inject.Inject


class TransactionRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : TransactionRepository {

    override suspend fun getTransactions(): List<TransactionEntity> {
        return try {
            val remoteData = remoteDataSource.getTransactions().transactions
            val entities = remoteData.map(::transactionResponseToEntity)
            localDataSource.insertAll(entities)
            localDataSource.getAll()
        } catch (e: NoInternetException) {
            localDataSource.getAll()
        }
    }

    override suspend fun restoreData(): List<TransactionEntity> {
        return try {
            val remoteData = remoteDataSource.getTransactions().transactions
            val newEntities = remoteData.map(::transactionResponseToEntity)
            return localDataSource.restoreData(newEntities)
        } catch (e: NoInternetException) {
            localDataSource.getAll()
        }
    }

    override suspend fun deleteAllTransactions() {
        localDataSource.deleteAllTransactions()
    }

    override suspend fun updateTransaction(transaction: TransactionEntity) {
        localDataSource.update(transaction)
    }

    private fun transactionResponseToEntity(t: TransactionResponse): TransactionEntity {
        return TransactionEntity(
            t.id,
            t.userId,
            t.createdDate,
            t.commerce.id,
            t.commerce.name,
            t.branch.name
        )
    }
}