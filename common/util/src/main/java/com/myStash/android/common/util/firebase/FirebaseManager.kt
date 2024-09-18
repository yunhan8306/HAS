package com.myStash.android.common.util.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseManager @Inject constructor() {

    companion object {
        private const val CRASHLYTICS_KEY_APP_STATE = "appState"
        const val CRASHLYTICS_VAL_BACKGROUND = "background"
        const val CRASHLYTICS_VAL_FOREGROUND = "foreground"
    }

    fun init(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun setCrashlyticsCustomAppState(state: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_APP_STATE, state)
    }
}