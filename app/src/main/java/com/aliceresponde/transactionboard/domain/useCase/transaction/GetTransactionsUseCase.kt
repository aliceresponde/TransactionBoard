package com.aliceresponde.transactionboard.domain.useCase.transaction

import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.model.Transaction

interface GetTransactionsUseCase {
    suspend fun getTransactions() : BusinessState<List <Transaction>>
    suspend fun restoreData(): BusinessState<List<Transaction>>
    suspend fun deleteAllTransactions(): BusinessState<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction, isNew : Boolean)
    suspend fun deleteTransaction(transaction: Transaction): BusinessState<List<Transaction>>
}
