package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.useCase.TransactionUseCases

class TransactionRepositoryImp (private val  useCase: TransactionUseCases) : TransactionRepository {
    override suspend fun getTransactions(): List<Transaction> {
        TODO("Not yet implemented")
    }
}