package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.remote.NoInternetException
import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse
import com.aliceresponde.transactionboard.data.remote.response.TransactionResponse
import com.aliceresponde.transactionboard.data.remote.response.UserResponse
import com.aliceresponde.transactionboard.data.repository.source.LocalDataSource
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource
import javax.inject.Inject


class TransactionRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : TransactionRepository {

    override suspend fun getTransactions(): List<TransactionEntity> {
        return try {
            val local = localDataSource.getAll()
            return if (local.isNotEmpty()) local
            else {
                val remoteData = remoteDataSource.getTransactions()
                val entities = remoteData.map(::transactionResponseToEntity)
                localDataSource.insertAll(entities)
                localDataSource.getAll()
            }
        } catch (e: NoInternetException) {
            localDataSource.getAll()
        }
    }

    override suspend fun restoreData(): List<TransactionEntity> {
        return try {
            val remoteData = remoteDataSource.getTransactions()
            val newEntities = remoteData.map(::transactionResponseToEntity)
            return localDataSource.restoreData(newEntities)
        } catch (e: NoInternetException) {
            localDataSource.getAll()
        }
    }

    override suspend fun deleteAllTransactions() {
        localDataSource.deleteAllTransactions()
    }

    override suspend fun deleteTransaction(transaction: TransactionEntity): List<TransactionEntity> {
        localDataSource.deleteTransaction(transaction)
        return localDataSource.getAll()
    }

    override suspend fun updateTransaction(transaction: TransactionEntity) {
        localDataSource.update(transaction)
    }

    override suspend fun getUserInfo(id: Int): UserResponse {
        return remoteDataSource.getUserById(id)
    }

    override suspend fun getTransactionInfo(id: Int): TransactionInfoResponse {
        return remoteDataSource.getTransactionInfo(id)
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