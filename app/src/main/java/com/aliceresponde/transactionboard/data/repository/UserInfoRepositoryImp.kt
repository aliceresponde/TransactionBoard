package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.Resource
import com.aliceresponde.transactionboard.data.SuccessResource
import com.aliceresponde.transactionboard.data.remote.NoInternetException
import com.aliceresponde.transactionboard.data.remote.response.UserResponse
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource

class UserInfoRepositoryImp(private val remoteDataSource: RemoteDataSource) :
    UserInfoRepository {
    override suspend fun getUserInfo(id: Int): Resource<UserResponse> {
        return try {
            val result = remoteDataSource.getUserInfo(id)
            SuccessResource(result)
        } catch (e: NoInternetException) {
            ErrorResource(Resource.INTERNET_ERROR_MESSAGE)
        }
    }
}