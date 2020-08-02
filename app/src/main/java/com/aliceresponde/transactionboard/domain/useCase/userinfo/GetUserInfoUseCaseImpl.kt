package com.aliceresponde.transactionboard.domain.useCase.userinfo

import com.aliceresponde.transactionboard.data.ErrorResource
import com.aliceresponde.transactionboard.data.SuccessResource
import com.aliceresponde.transactionboard.data.repository.UserInfoRepository
import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.ErrorState
import com.aliceresponde.transactionboard.domain.SuccessState
import com.aliceresponde.transactionboard.domain.model.User

class GetUserInfoUseCaseImpl(private val repository: UserInfoRepository) : GetUserInfoUseCase{
    override suspend fun getUserInfo(id: Int): BusinessState<User> {
        when (val response = repository.getUserInfo(id)) {
            is SuccessResource -> {
                response.data?.let {
                    return SuccessState(User(it.id, it.name, it.photo))
                }?: return ErrorState("")
            }
            is ErrorResource -> return ErrorState(response.message ?: "")
        }
    }
}