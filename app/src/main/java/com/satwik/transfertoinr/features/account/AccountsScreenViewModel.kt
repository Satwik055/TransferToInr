package com.satwik.transfertoinr.features.account

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import kotlinx.coroutines.launch

class AccountsScreenViewModel(private val authRepository: AuthRepository):ViewModel() {

    fun logout(){
        viewModelScope.launch {
            println("Logout clicked")
            authRepository.logout()
        }
    }
}