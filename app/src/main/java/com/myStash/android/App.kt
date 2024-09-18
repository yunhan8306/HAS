package com.myStash.android

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.myStash.android.common.util.firebase.FirebaseProcessLifecycleObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var firebaseProcessLifecycleObserver: FirebaseProcessLifecycleObserver

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(firebaseProcessLifecycleObserver.lifecycleObserver)
    }
}