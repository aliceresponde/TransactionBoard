package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.Resource
import com.aliceresponde.transactionboard.data.remote.response.TransactionInfoResponse

interface TransactionInfoRepository {
    suspend fun getTransactionInfo(id: Int): Resource<TransactionInfoResponse>
}
