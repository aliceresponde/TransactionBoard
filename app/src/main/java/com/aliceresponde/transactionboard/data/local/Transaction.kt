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

    val points: Int = 0,
    val clientName: String = "",
    val value: Int = 0,

    val isNew: Boolean = true
)