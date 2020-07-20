package com.aliceresponde.transactionboard.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val userId: Int,
    val createdDate: String,
    val commerceName: String,
    val branchName: String,

    val points: Int,
    val clientName: String,
    val value: Int,
    val isNew: Boolean
)