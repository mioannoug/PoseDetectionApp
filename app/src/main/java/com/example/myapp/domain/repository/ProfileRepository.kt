package com.example.myapp.domain.repository

import com.example.myapp.Resource

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>
}