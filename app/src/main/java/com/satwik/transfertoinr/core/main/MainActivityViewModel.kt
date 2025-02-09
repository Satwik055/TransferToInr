package com.satwik.transfertoinr.core.main

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.features.auth.login.LoginScreenState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivityViewModel(private val supabaseClient: SupabaseClient):ViewModel() {

    private val _mainActivityState = mutableStateOf(MainActivityState())
    val mainActivityState: State<MainActivityState> = _mainActivityState

    private val _authInitialized = MutableStateFlow(false)
    val authInitialized: StateFlow<Boolean> = _authInitialized.asStateFlow()

    init {
            viewModelScope.launch {
                supabaseClient.auth.sessionStatus.collectLatest { status ->
                    println(status)

                    when(status){
                        is SessionStatus.Authenticated -> _mainActivityState.value = MainActivityState(isUserLoggedIn=true)
                        is SessionStatus.Initializing -> _authInitialized.value = true
                        else -> MainActivityState(isUserLoggedIn = false)
                    }
                }
            }
        }
}
