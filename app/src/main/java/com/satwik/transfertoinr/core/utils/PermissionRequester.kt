package com.satwik.transfertoinr.core.utils

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionRequester(modifier: Modifier = Modifier) {
    val permissions = listOf(Manifest.permission.POST_NOTIFICATIONS)
    val deniedPermissions = remember { mutableStateOf(listOf<String>()) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        deniedPermissions.value = result.filterValues { !it }.keys.toList()
        if (deniedPermissions.value.isEmpty()) {
            println("All permissions granted")
        } else {
            println("Denied Permissions: $deniedPermissions")
        }
    }

    LaunchedEffect(Unit) {
        if (deniedPermissions.value.isNotEmpty()) {
            launcher.launch(deniedPermissions.value.toTypedArray())
        } else {
            launcher.launch(permissions.toTypedArray())
        }

    }
}