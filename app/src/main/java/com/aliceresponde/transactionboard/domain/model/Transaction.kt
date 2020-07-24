package com.aliceresponde.transactionboard.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: Int,
    val userId: Int,
    val commerceId: Int,
    val createdDate: String,
    val commerceName: String,
    val branchName: String,
    val isNew: Boolean = true
) : Parcelable
