package com.aliceresponde.transactionboard.data.repository

import com.aliceresponde.transactionboard.data.remote.response.UserResponse

interface UserInfoRepository {
     suspend fun getUserInfo(id: Int): UserResponse
}
