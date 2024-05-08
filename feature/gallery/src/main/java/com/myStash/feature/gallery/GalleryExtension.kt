package com.myStash.feature.gallery

import android.content.ContentUris
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import com.myStash.core.model.Image

fun ComponentActivity.launchGalleryTestActivity() {
    Intent(this, GalleryActivity::class.java).apply {
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