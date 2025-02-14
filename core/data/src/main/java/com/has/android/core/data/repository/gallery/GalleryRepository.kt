package com.has.android.core.data.repository.gallery

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.provider.MediaStore
import com.has.android.core.model.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor(
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
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
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
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val folderIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID)
            val folderColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            if(idColumn.checkIndex() || nameColumn.checkIndex() || folderIdColumn.checkIndex() || folderColumn.checkIndex()) return@use emptyList()

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val folderId = cursor.getLong(folderIdColumn)
                val folder = cursor.getString(folderColumn)

                try {
                    imageList.add(Image(id, name, uri, folderId, folder))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

    private fun Int.checkIndex() = equals(-1)
}