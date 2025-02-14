package com.has.android.common.util.firebase

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.has.android.common.util.safeLaunch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseProcessLifecycleObserver @Inject constructor(
    private val application: Application,
    private val firebaseManager: FirebaseManager
) {

    private val internalProcessLifecycleObserver = InternalProcessLifecycleObserver(
        onBackground = ::emitBackground,
        onForeground = ::emitForeground
    )

    val lifecycleObserver: LifecycleObserver get() = internalProcessLifecycleObserver

    init {
        firebaseManager.init(application)
    }

    private fun emitBackground() {
        firebaseManager.setCrashlyticsCustomAppState(FirebaseManager.CRASHLYTICS_VAL_BACKGROUND)
    }

    private fun emitForeground() {
        firebaseManager.setCrashlyticsCustomAppState(FirebaseManager.CRASHLYTICS_VAL_FOREGROUND)
    }
}

private class InternalProcessLifecycleObserver(
    private val onBackground: () -> Unit,
    private val onForeground: () -> Unit,
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        owner.lifecycleScope.safeLaunch { onForeground.invoke() }
    }

    override fun onStop(owner: LifecycleOwner) {
        owner.lifecycleScope.safeLaunch { onBackground.invoke() }
    }
}