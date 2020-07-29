package com.aliceresponde.transactionboard.domain.useCase.transaction

import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.repository.TransactionRepository
import com.aliceresponde.transactionboard.domain.model.Transaction

class GetTransactionsUseCaseImp(private val repository: TransactionRepository) :
    GetTransactionsUseCase {
    override suspend fun getTransactions(): List<Transaction> {
        val data = repository.getTransactions()
        return data.map(::transactionEntityToTransaction)
    }

    override suspend fun restoreData(): List<Transaction> {
        val transactionEntities = repository.restoreData()
        return transactionEntities.map(::transactionEntityToTransaction)
    }

    override suspend fun deleteAllTransactions(): List<Transaction> {
        repository.deleteAllTransactions()
        return listOf()
    }

    override suspend fun updateTransaction(transaction: Transaction, isNew: Boolean) {
        transaction.apply { this.isNew = isNew }
        val transactionEntity = transactionToEntity(transaction)
        repository.updateTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(transaction: Transaction): List<Transaction> {
        val entity =TransactionEntity(
            transaction.id,
            transaction.userId,
            transaction.createdDate,
            transaction.commerceId,
            transaction.commerceName,
            transaction.branchName,
            isNew = transaction.isNew
        )
        val result = repository.deleteTransaction(entity)
        return  result.map (::transactionEntityToTransaction)
    }

    private fun transactionToEntity(transaction: Transaction): TransactionEntity =
        TransactionEntity(
            transaction.id,
            transaction.userId,
            transaction.createdDate,
            transaction.commerceId,
            transaction.commerceName,
            transaction.branchName,
            isNew = transaction.isNew
        )

    private fun transactionEntityToTransaction(entity: TransactionEntity): Transaction {
        return Transaction(
            entity.id,
            entity.userId,
            entity.commerceId,
            entity.createdDate,
            entity.commerceName,
            entity.branchName,
            entity.isNew
        )
    }
}