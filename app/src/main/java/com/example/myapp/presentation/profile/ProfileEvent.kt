package com.example.myapp.presentation.profile

import com.example.myapp.domain.repository.Profile

sealed class ProfileEvent{
    data class GetProfile(val userId: String): ProfileEvent()

}
