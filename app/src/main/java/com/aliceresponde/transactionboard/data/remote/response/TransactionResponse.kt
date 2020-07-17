package com.aliceresponde.transactionboard.data.remote.response

data class TransactionResponse(
    val id: Int,
    val userId: Int,
    val createdDate: String,
    val commerce: CommerceResponse,
    val branches: List<BranchResponse>,
    val branch: BranchResponse
)
