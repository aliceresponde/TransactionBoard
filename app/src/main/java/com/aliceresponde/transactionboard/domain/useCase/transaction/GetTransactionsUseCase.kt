package com.aliceresponde.transactionboard.domain.useCase.transaction

import com.aliceresponde.transactionboard.domain.model.Transaction

interface GetTransactionsUseCase {
    suspend fun getTransactions() : List <Transaction>
    suspend fun restoreData(): List<Transaction>
    suspend fun deleteAllTransactions(): List<Transaction>
    suspend fun updateTransaction(transaction: Transaction, isNew : Boolean)
}
