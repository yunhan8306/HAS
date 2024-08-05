package com.myStash.android.core.model

import android.net.Uri

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri,
    val folderId: Long,
    val folder: String,
    var isSelected: Boolean = false,
) {
    fun select() {
        isSelected = !isSelected
    }
}

fun List<Image>.getFolderList(): List<ImageFolder> {
    return distinctBy { it.folderId }.map { image ->
        val cnt = count { it.folderId == image.folderId }
        ImageFolder(
            id = image.folderId,
            name = image.folder,
            uri = image.uri,
            cnt = cnt
        )
    }
}

data class ImageFolder(
    val id: Long? = null,
    val name: String = "Total",
    val uri: Uri = Uri.EMPTY,
    val cnt: Int = 0
)

fun List<ImageFolder>.addTotalFolder(packageName: String, imgCnt: Int): List<ImageFolder> {
    return toMutableList().apply {
        val totalFolder = if(isEmpty()) {
            ImageFolder(
                id = null,
                name = "Total",
                uri = Uri.parse("android.resource://$packageName/common/resource/drawable/img_folder_empty"),
                cnt = imgCnt
            )
        } else {
            ImageFolder(
                id = null,
                name = "Total",
                uri = first().uri,
                cnt = imgCnt
            )
        }
        add(0, totalFolder)
    }.toList()
}

fun Array<String>.getImageList(imageList: List<Image>): List<Image> {
    return imageList.filter { contains(it.uri.toString()) }
}

fun Array<String>.getFirstImage(imageList: List<Image>): Image? {
    return imageList.firstOrNull { contains(it.uri.toString()) }
}