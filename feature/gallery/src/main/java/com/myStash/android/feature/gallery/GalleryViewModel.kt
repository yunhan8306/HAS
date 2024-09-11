package com.myStash.android.feature.gallery

import android.app.Application
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.AppConfig
import com.myStash.android.common.util.checkContain
import com.myStash.android.common.util.offerFirst
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Image
import com.myStash.android.core.model.ImageFolder
import com.myStash.android.core.model.addTotalFolder
import com.myStash.android.core.model.getFirstImage
import com.myStash.android.core.model.getFolderList
import com.myStash.android.core.model.getImageList
import com.myStash.android.feature.gallery.GalleryConstants.AGO_IMAGE_URI_ARRAY
import com.myStash.android.feature.gallery.GalleryConstants.MULTI
import com.myStash.android.feature.gallery.GalleryConstants.MULTI_CNT
import com.myStash.android.feature.gallery.GalleryConstants.SINGLE
import com.myStash.android.feature.gallery.GalleryConstants.TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val application: Application,
    private val imageRepository: ImageRepository,
    private val stateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ContainerHost<GalleryScreenState, GallerySideEffect>, ViewModel() {

    override val container: Container<GalleryScreenState, GallerySideEffect> =
        container(GalleryScreenState())

    init {
        fetch()
    }

    private val _selectedList = mutableListOf<Image>()
    private val selectedList get() = _selectedList.toList()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                val type = stateHandle.get<String>(TYPE) ?: SINGLE
                val agoImageArray = stateHandle.get<Array<String>>(AGO_IMAGE_URI_ARRAY) ?: emptyArray()

                getGalleryImages()
                imageRepository.imagesStateFlow.collectLatest { imageList ->
                    AppConfig.allowReadMediaVisualUserSelected = imageList.size > 30
                    val imageFolderList = imageList.getFolderList().addTotalFolder(application.packageName, imageList.size)
                    val agoImageList = agoImageArray.getImageList(imageList)
                    _selectedList.addAll(agoImageList)
                    reduce {
                        state.copy(
                            type = type,
                            focusImage = agoImageArray.getFirstImage(imageList) ?: imageList.firstOrNull(),
                            selectedFolder = imageFolderList.first(),
                            imageList = imageList,
                            imageFolderList = imageFolderList,
                            selectedImageList = agoImageList
                        )
                    }
                }
            }
        }
    }

    fun getGalleryImages() {
        viewModelScope.launch(ioDispatcher) {
            imageRepository.init()
        }
    }

    private fun setGalleryImage(images: List<Image>) {
        intent {
            reduce {
                state.copy(imageList = images)
            }
        }
    }

    fun onAction(action: GalleryAction) {
        when (action) {
            is GalleryAction.AddSelectedList -> {
                addSelectedList(action.image)
            }
            is GalleryAction.Focus -> {
                onFocus(action.image)
            }
            is GalleryAction.SelectFolder -> {
                selectFolder(action.imageFolder)
            }
            is GalleryAction.Complete -> {
                complete()
            }
        }
    }

    private fun addSelectedList(image: Image) {
        intent {
            when(state.type) {
                SINGLE -> _selectedList.offerFirst(image)
                MULTI -> {
                    when {
                        _selectedList.checkContain { it.name == image.name } -> {
                            _selectedList.offerOrRemove(image) { it.name == image.name }
                        }
                        _selectedList.size >= MULTI_CNT -> {
                            postSideEffect(GallerySideEffect.OverSelect)
                        }
                        else -> {
                            _selectedList.offerOrRemove(image) { it.name == image.name }
                        }
                    }
                }
            }
            reduce {
                state.copy(
                    focusImage = if(selectedList.isNotEmpty()) selectedList.last() else image,
                    selectedImageList = _selectedList.toList()
                )
            }
        }
    }

    private fun onFocus(image: Image) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        focusImage = image
                    )
                }
            }
        }
    }

    private fun selectFolder(imageFolder: ImageFolder) {
        intent {
            viewModelScope.launch(ioDispatcher) {
                reduce {
                    state.copy(
                        selectedFolder = imageFolder,
                        imageList = imageRepository.imagesStateFlow.value.run {
                            if(imageFolder.id == null) this else filter { it.folderId == imageFolder.id }
                        }
                    )
                }
            }
        }
    }

    private fun complete() {
        intent {
            viewModelScope.launch {
                Intent().apply {
                    when(state.type) {
                        SINGLE -> putExtra(SINGLE, selectedList[0].uri.toString())
                        MULTI -> putExtra(MULTI, selectedList.map { it.uri.toString() }.toTypedArray())
                    }
                    postSideEffect(GallerySideEffect.Complete(this))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageRepository.cleanup()
    }
}

sealed interface GalleryAction {
    data class AddSelectedList(val image: Image): GalleryAction
    data class Focus(val image: Image): GalleryAction
    data class SelectFolder(val imageFolder: ImageFolder): GalleryAction
    object Complete: GalleryAction
}