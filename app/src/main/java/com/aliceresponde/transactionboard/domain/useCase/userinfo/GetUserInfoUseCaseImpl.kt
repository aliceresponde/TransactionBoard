package com.aliceresponde.transactionboard.domain.useCase.userinfo

import com.aliceresponde.transactionboard.data.repository.UserInfoRepository
import com.aliceresponde.transactionboard.domain.model.User

class GetUserInfoUseCaseImpl(private val repository: UserInfoRepository) : GetUserInfoUseCase{
    override suspend fun getUserInfo(id: Int): User {
        val response = repository.getUserInfo(id)
        return User(response.id, response.name, response.photo)
    }
}