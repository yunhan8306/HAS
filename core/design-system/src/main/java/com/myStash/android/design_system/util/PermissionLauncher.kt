package com.myStash.android.design_system.util

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun rememberPermissionLauncher(
    activity: ComponentActivity,
    scope: CoroutineScope,
    grant: () -> Unit,
    denied: () -> Unit
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    scope.launch {
        var isGranted = true
        var isDenied = false
        permissions.entries.forEach {
            val agoGranted = it.value
            val agoDenied = ActivityCompat.shouldShowRequestPermissionRationale(activity, it.key)
            if(agoDenied) {
                isDenied = true
                return@forEach
            }
            if(!agoGranted) {
                isGranted = false
                return@forEach
            }
        }
        when {
            isGranted -> grant.invoke()
            isDenied -> denied.invoke()
        }
    }
}