package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.remote.response.UserResponse
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource

class UserInfoRepositoryImp (private val remoteDataSource: RemoteDataSource) : UserInfoRepository {
    override suspend fun getUserInfo(id: Int): UserResponse {
        return remoteDataSource.getUserInfo(id)
    }
}