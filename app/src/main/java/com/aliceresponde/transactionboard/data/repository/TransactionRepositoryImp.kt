package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.Resource
import com.aliceresponde.transactionboard.data.Resource.Companion.INTERNAL_DB_ERROR_MESSAGE
import com.aliceresponde.transactionboard.data.Resource.Companion.INTERNET_ERROR_MESSAGE
import com.aliceresponde.transactionboard.data.SuccessResource
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

    override suspend fun getTransactions(): Resource<List<TransactionEntity>> {
        return try {
            val local = localDataSource.getAll()
            return if (local.isNotEmpty()) SuccessResource(local)
            else {
                val remoteData = remoteDataSource.getTransactions()
                val entities = remoteData.map(::transactionResponseToEntity)
                localDataSource.insertAll(entities)
                SuccessResource(localDataSource.getAll())
            }
        } catch (e: NoInternetException) {
            ErrorResource(INTERNET_ERROR_MESSAGE)
        }
    }

    override suspend fun restoreData(): Resource<List<TransactionEntity>> {
        return try {
            val remoteData = remoteDataSource.getTransactions()
            val newEntities = remoteData.map(::transactionResponseToEntity)
            SuccessResource(localDataSource.restoreData(newEntities))
        } catch (e: NoInternetException) {
            ErrorResource(INTERNET_ERROR_MESSAGE)
        }
    }

    override suspend fun deleteAllTransactions() = localDataSource.deleteAllTransactions()

    override suspend fun deleteTransaction(transaction: TransactionEntity): Resource<List<TransactionEntity>> {
        return try {
            localDataSource.deleteTransaction(transaction)
            val data = localDataSource.getAll()
            SuccessResource(data)
        } catch (e: Exception) {
            ErrorResource(INTERNAL_DB_ERROR_MESSAGE)
        }
    }

    override suspend fun updateTransaction(transaction: TransactionEntity) {
        localDataSource.update(transaction)
    }

    override suspend fun getUserInfo(id: Int): Resource<UserResponse> {
        return try {
            val data = remoteDataSource.getUserById(id)
            SuccessResource(data)
        } catch (e: NoInternetException) {
            ErrorResource(INTERNET_ERROR_MESSAGE)
        }
    }

    override suspend fun getTransactionInfo(id: Int): Resource<TransactionInfoResponse> {
        return try {
            val data = remoteDataSource.getTransactionInfo(id)
            SuccessResource(data)
        } catch (e: NoInternetException) {
            ErrorResource(message = INTERNET_ERROR_MESSAGE)
        }
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