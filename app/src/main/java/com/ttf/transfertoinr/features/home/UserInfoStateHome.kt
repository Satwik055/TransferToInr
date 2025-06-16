package com.ttf.transfertoinr.features.home

import com.ttf.transfertoinr.core.model.Profile

data class UserInfoStateHome(
    val profile: Profile = Profile(),
    val isLoading: Boolean = false,
    val error: String = ""
)
