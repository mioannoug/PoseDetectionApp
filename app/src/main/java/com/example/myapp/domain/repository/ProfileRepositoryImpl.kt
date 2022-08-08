package com.example.myapp.domain.repository

import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.data.remote.ProfileApi
import com.example.myapp.data.request.CreateAccountRequest
import com.example.myapp.presentation.UiText
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl (
    private val api: ProfileApi
        ) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = api.getProfile(userId)
            if (response.successful) {
                Resource.Success(response.data?.toProfile())
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unkown))

            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_something_went_wrong)
            )
        }
    }
}