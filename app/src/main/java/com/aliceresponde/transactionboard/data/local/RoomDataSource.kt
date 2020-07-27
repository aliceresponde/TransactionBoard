package com.aliceresponde.transactionboard.data.local

import com.aliceresponde.transactionboard.data.repository.source.LocalDataSource
import javax.inject.Inject

class RoomDataSource @Inject constructor( private  val db: AppDatabase) : LocalDataSource {
    private val transactionDao = db.transactionDao()

    override suspend fun getAll(): List<TransactionEntity> = transactionDao.getAllTransactions()

    override suspend fun insertAll(transactions: List<TransactionEntity>) {
        transactionDao.insertAll(transactions)
    }

    override suspend fun update(transaction: TransactionEntity) {
        transactionDao.updateTransaction(transaction)
    }

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAll()
    }

    override suspend fun restoreData(transactions: List<TransactionEntity>): List<TransactionEntity> {
        return transactionDao.restoreData(transactions)
    }
}
