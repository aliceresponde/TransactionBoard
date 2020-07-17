package com.aliceresponde.transactionboard.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: String,
    val value: Int,
    val points: Int,
    val date: String,
    val commerceName: String,
    val commercePlace: String,
    val clientName: String,
    val isNew: Boolean
) : Parcelable
