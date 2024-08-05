package com.myStash.android.feature.gallery.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.myStash.android.common.util.AppConfig

object PermissionUtil {
    fun ComponentActivity.galleryPermissionCheck(
        permissions: Array<String>,
        requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        onPermissionDenied: () -> Unit,
    ) {
        val hasUserSelectedPermission = checkPermission(this, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        val hasImagePermission = checkPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
        val deniedBeforePermission = checkDeniedBefore(this, permissions)

        when {
            hasUserSelectedPermission && !hasImagePermission -> {
                AppConfig.allowReadMediaVisualUserSelected = false
                permissions.forEach { requestPermissionLauncher.launch(it) }
            }
            deniedBeforePermission.isNotEmpty() -> onPermissionDenied.invoke()
            else -> {
                if(!hasUserSelectedPermission) AppConfig.allowReadMediaVisualUserSelected = false
                permissions.forEach { requestPermissionLauncher.launch(it) }
            }
        }
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkDeniedBefore(activity: Activity, permissions: Array<String>): String {
        var deniedPermission = ""
        permissions.forEach { permission ->
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                deniedPermission = permission
                return@forEach
            }
        }
        return deniedPermission
    }
}