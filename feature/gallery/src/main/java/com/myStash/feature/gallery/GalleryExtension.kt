package com.myStash.feature.gallery

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import com.myStash.core.model.Image

fun ComponentActivity.launchGalleryTestActivity(
    activityResultCallback: (ActivityResult) -> Unit = {}
) {
    val registerKey = "item"

    val contract = object : ActivityResultContract<Intent, ActivityResult>() {
        override fun createIntent(context: Context, input: Intent) = input
        override fun parseResult(resultCode: Int, intent: Intent?) = ActivityResult(resultCode, intent)
    }

    val launcher by lazy {
        activityResultRegistry.register(registerKey, contract) { activityResult ->
            Log.d("qwe123", "activityResult - $activityResult")
            if (activityResult.resultCode == Activity.RESULT_OK) {
                activityResultCallback.invoke(activityResult)
            }
        }
    }

    Intent(this, GalleryActivity::class.java).apply {
        launcher.launch(this)
        startActivity(this)
    }
}

fun ComponentActivity.getPhotoList(callback: (List<Image>) -> Unit) {
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
    )

    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
    val images = mutableListOf<Image>()

    contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        null,
        null,
        sortOrder
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

            images.add(Image(id, name, uri))
        }
        callback.invoke(images)
    }
}