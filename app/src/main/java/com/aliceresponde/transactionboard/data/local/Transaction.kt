package com.aliceresponde.transactionboard.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val createdDate: String,
    val commerceId: Int,
    val commerceName: String,
    val branchName: String,

    val isNew: Boolean = true
)