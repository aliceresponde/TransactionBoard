package com.aliceresponde.transactionboard.domain.useCase.transactionInfo

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.SuccessResource
import com.aliceresponde.transactionboard.data.repository.TransactionInfoRepository
import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.ErrorState
import com.aliceresponde.transactionboard.domain.SuccessState
import com.aliceresponde.transactionboard.domain.model.TransactionInfo

class GetTransactionsInfoUseCaseImp(
    private val repository: TransactionInfoRepository
) : GetTransactionsInfoUseCase {

    override suspend fun getTransactionInfo(id: Int): BusinessState<TransactionInfo> {
        return when (val remoteInfo = repository.getTransactionInfo(id)) {
            is SuccessResource -> {
                val data = remoteInfo.data!!
                SuccessState(
                    TransactionInfo(
                        data.transactionId,
                        data.value,
                        data.points
                    )
                )
            }
            is ErrorResource -> ErrorState(remoteInfo.message ?: "")
        }
    }
}