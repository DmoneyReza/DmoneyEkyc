package com.example.dmoney.auth.domain.usecase



import com.example.dmoney.auth.domain.model.Model
import com.example.dmoney.auth.domain.repository.ServiceRepository
import com.example.dmoneyekyc.util.Resource

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenCase @Inject constructor(
        val repo:ServiceRepository
) {
    suspend operator  fun invoke(): Flow<Resource<Model>> {
        return  repo.getAccessToken()

    }

}