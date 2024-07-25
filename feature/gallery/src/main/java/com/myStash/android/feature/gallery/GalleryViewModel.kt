package com.myStash.android.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.AppConfig
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ContainerHost<GalleryScreenState, GallerySideEffect>, ViewModel() {

    override val container: Container<GalleryScreenState, GallerySideEffect> =
        container(GalleryScreenState())

    init {
        fetch()
    }

    private val _selectedList = mutableListOf<Image>()
    val selectedList get() = _selectedList.toList()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                getGalleryImages()
                imageRepository.imagesStateFlow.collectLatest { imageList ->
                    AppConfig.allowReadMediaVisualUserSelected = imageList.size > 30
                    reduce {
                        state.copy(imageList = imageList)
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
        }
    }

    private fun addSelectedList(image: Image) {
        intent {
            _selectedList.offerOrRemove(image) { it.name == image.name }
            reduce {
                state.copy(
                    focusImage = image,
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

    override fun onCleared() {
        super.onCleared()
        imageRepository.cleanup()
    }
}

sealed interface GalleryAction {
    data class AddSelectedList(val image: Image): GalleryAction
    data class Focus(val image: Image): GalleryAction
}