package com.myStash.feature.gallery

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.provider.MediaStore
import com.myStash.core.model.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

fun getPhotoList(context: Context): List<Image> {
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
    )

    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
    val images = mutableListOf<Image>()

    return context.contentResolver.query(
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
        images
    } ?: emptyList()
}

@Singleton
class ImageRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _imagesStateFlow = MutableStateFlow(emptyList<Image>())
    val imagesStateFlow = _imagesStateFlow.asStateFlow()

    private val contentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            loadImages()
        }
    }

    fun loadImages() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val imageList = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val imageList = mutableListOf<Image>()
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                imageList.add(Image(id, name, uri))
            }
            imageList
        } ?: emptyList()
        _imagesStateFlow.tryEmit(imageList)
    }

    fun init() {
        loadImages()
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    fun cleanup() {
        context.contentResolver.unregisterContentObserver(contentObserver)
    }
}