package com.aliceresponde.transactionboard.data


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        const val INTERNAL_DB_ERROR_MESSAGE = "Local database error"
        const val INTERNET_ERROR_MESSAGE = "No internet Access"
    }
}

class SuccessResource<T>(data: T) : Resource<T>(data)
class ErrorResource<T>(message: String = "", data: T? = null) : Resource<T>(data, message)