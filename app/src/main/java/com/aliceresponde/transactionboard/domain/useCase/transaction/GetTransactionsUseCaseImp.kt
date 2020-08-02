package com.aliceresponde.transactionboard.domain.useCase.transaction

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.SuccessResource
import com.aliceresponde.transactionboard.data.local.TransactionEntity
import com.aliceresponde.transactionboard.data.repository.TransactionRepository
import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.ErrorState
import com.aliceresponde.transactionboard.domain.SuccessState
import com.aliceresponde.transactionboard.domain.model.Transaction

class GetTransactionsUseCaseImp(private val repository: TransactionRepository) :
    GetTransactionsUseCase {
    override suspend fun getTransactions(): BusinessState<List<Transaction>> {
        when (val resource = repository.getTransactions()) {
            is SuccessResource ->
                resource.data?.let {
                    return SuccessState(it.map(::transactionEntityToTransaction))
                } ?: return SuccessState(listOf())
            is ErrorResource -> return ErrorState(resource.message ?: "")
        }
    }

    override suspend fun restoreData(): BusinessState<List<Transaction>> {
        return when (val resource = repository.restoreData()) {
            is SuccessResource -> {
                val data =
                    resource.data?.let { it.map(::transactionEntityToTransaction) } ?: listOf()
                SuccessState(data)
            }
            is ErrorResource -> ErrorState(resource.message ?: "")
        }
    }

    override suspend fun deleteAllTransactions(): BusinessState<List<Transaction>> {
        repository.deleteAllTransactions()
        return SuccessState(listOf())
    }

    override suspend fun updateTransaction(transaction: Transaction, isNew: Boolean) {
        transaction.apply { this.isNew = isNew }
        val transactionEntity = transactionToEntity(transaction)
        repository.updateTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(transaction: Transaction): BusinessState<List<Transaction>> {
        val entity = TransactionEntity(
            transaction.id,
            transaction.userId,
            transaction.createdDate,
            transaction.commerceId,
            transaction.commerceName,
            transaction.branchName,
            isNew = transaction.isNew
        )
        when (val result = repository.deleteTransaction(entity)) {
            is SuccessResource -> {
                result.data?.let {
                    val data = it.map(::transactionEntityToTransaction)
                    return SuccessState(data)
                } ?: return SuccessState(listOf())
            }
            is ErrorResource -> return ErrorState(result.message ?: "")
        }
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
            entity.createdDate.subSequence(0, 10) as String,
            entity.commerceName,
            entity.branchName,
            entity.isNew
        )
    }
}