package com.ttf.transfertoinr.core.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivityViewModel(private val supabaseClient: SupabaseClient):ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing = _isInitializing.asStateFlow()

    init {
        observeSessionStatus()
    }

    private fun observeSessionStatus() {
        viewModelScope.launch {
            supabaseClient.auth.sessionStatus.collectLatest { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        _isLoggedIn.value = true
                        _isInitializing.value = false
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _isLoggedIn.value = false
                        _isInitializing.value = false
                    }
                    is SessionStatus.Initializing -> {
                        _isInitializing.value = true
                    }
                    is SessionStatus.RefreshFailure -> _isInitializing.value = true
                }
            }
        }
    }






//
//    private fun checkLoginStatus(){
//        runBlocking {
//            val user = supabaseClient.auth.sessionManager.loadSession()?.user
//            println(user)
//            when(user){
//                null-> _isLoggedIn.value = false
//                else-> _isLoggedIn.value = true
//            }
//        }
//    }
//
//    private fun splashScreenDelay(timeMillis: Long){
//        viewModelScope.launch {
//            delay(timeMillis)
//            _isReady.value = true
//        }
//    }
//


}




