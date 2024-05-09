package com.myStash.feature.gallery

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myStash.core.model.Image
import com.myStash.design_system.ui.theme.NoRippleTheme
import com.myStash.design_system.ui.theme.clickableNoRipple

@Composable
fun GalleryItem(
    image: Image,
    selectedList: List<Image>,
    onSelect: (Image) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    LaunchedEffect(selectedList) {
        isSelected = selectedList.firstOrNull { it.name == image.name }?.let { true } ?: false
    }

    Box {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { onSelect.invoke(image) },
            model = image.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(if (isSelected) Color.Blue else Color.Yellow)
        )
    }
}

@Composable
fun GalleryItem2(
    image: Image,
    selectedList: List<Image>,
    onSelect: (Image) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf(image.uri) }

    LaunchedEffect(selectedList) {
        Log.d("qwe123", "selectedList - $selectedList")
        isSelected = selectedList.firstOrNull { it.name == image.name }?.let { true } ?: false
    }

    LaunchedEffect(image) {
        if(image.uri != imageUri) {
            Log.d("qwe123", "uri 변경됌 - ${image.uri}")
            imageUri = image.uri
        }
    }

    Log.d("qwe123", "리컴포지션 - $image")

    Box {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { onSelect.invoke(image) },
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(if (isSelected) Color.Blue else Color.Yellow)
        )
    }
}

@Composable
fun GalleryItem3(
    image: Image,
    onSelect: (Image) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    val onClick: (Image) -> Unit = {
        isSelected = !isSelected
        if(isSelected) onSelect.invoke(it.copy(isSelected = true))
    }
    Log.d("qwe123", "리컴포지션 - $image")

    Box {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { onClick.invoke(image) },
            model = image.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(if (isSelected) Color.Blue else Color.Yellow)
        )
    }
}

@Composable
fun GalleryItem4(
    imageUri: Uri,
    selectedNumber: Int?,
    onSelect: () -> Unit
) {

    LaunchedEffect(selectedNumber) {
        Log.d("qwe123", "selectedNumber - $selectedNumber")
    }

    NoRippleTheme {


        Box {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickableNoRipple { onSelect.invoke() },
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            selectedNumber?.let {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Yellow)
                ) {
                    Text(it.toString())
                }
            }
        }
    }

}