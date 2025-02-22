package com.satwik.transfertoinr.core.main

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivityViewModel(private val supabaseClient: SupabaseClient):ViewModel() {

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        splashScreenDelay(1000)
        checkLoginStatus()

    }



    private fun checkLoginStatus(){
        viewModelScope.launch {
            supabaseClient.auth.sessionStatus.collectLatest { status ->
                when(status){
                    is SessionStatus.Authenticated -> _isLoggedIn.value = true
                    else -> _isLoggedIn.value = false
                }
            }
        }
    }

    private fun splashScreenDelay(timeMillis: Long){
        viewModelScope.launch {
            delay(timeMillis)
            _isReady.value = true
        }
    }
}
