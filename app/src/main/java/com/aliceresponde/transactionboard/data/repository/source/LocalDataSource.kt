package com.aliceresponde.transactionboard.data.repository.source

import com.aliceresponde.transactionboard.data.local.TransactionEntity


interface LocalDataSource {
    suspend fun insertAll(transactions: List<TransactionEntity>)
    suspend fun getAll(): List<TransactionEntity>
    suspend fun update(transaction: TransactionEntity)
    suspend fun deleteAllTransactions()
    suspend fun restoreData(transactions: List<TransactionEntity>): List<TransactionEntity>
}
