package com.aliceresponde.transactionboard.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_table")
    fun getAllTransactions(): List<TransactionEntity>

    @Insert(onConflict = IGNORE)
    suspend fun insertAll(transaction: List<TransactionEntity>)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Transaction
    suspend fun restoreData(transactions: List<TransactionEntity>): List<TransactionEntity> {
        deleteAll()
        insertAll(transactions)
        return getAllTransactions()
    }
}