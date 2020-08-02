package com.aliceresponde.transactionboard.domain.useCase.transactionInfo

import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.model.TransactionInfo

interface GetTransactionsInfoUseCase {
    suspend fun getTransactionInfo(id: Int): BusinessState<TransactionInfo>
}
