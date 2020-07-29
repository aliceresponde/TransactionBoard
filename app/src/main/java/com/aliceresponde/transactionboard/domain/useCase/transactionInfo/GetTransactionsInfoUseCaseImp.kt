package com.aliceresponde.transactionboard.domain.useCase.transactionInfo

import com.aliceresponde.transactionboard.data.repository.TransactionInfoRepository
import com.aliceresponde.transactionboard.domain.model.TransactionInfo

class GetTransactionsInfoUseCaseImp(
    private val repository: TransactionInfoRepository
) : GetTransactionsInfoUseCase {

    override suspend fun getTransactionInfo(id: Int): TransactionInfo {
        val remoteInfo = repository.getTransactionInfo(id)
        return TransactionInfo(
            remoteInfo.transactionId,
            remoteInfo.value,
            remoteInfo.points
        )
    }
}