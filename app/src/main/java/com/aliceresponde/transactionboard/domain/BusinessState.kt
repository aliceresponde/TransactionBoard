package com.aliceresponde.transactionboard.domain


sealed class BusinessState<T>(
    val data: T? = null,
    val message: String? = null
) {
    companion object{
        const val EMPTY_DATA_MESSAGE = "No Data Available to show"
    }
}
class SuccessState<T>(data: T) : BusinessState<T>(data)
class ErrorState<T>(message: String, data: T? = null) : BusinessState<T>(data, message)