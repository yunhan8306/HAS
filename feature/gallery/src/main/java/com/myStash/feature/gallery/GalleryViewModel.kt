package com.myStash.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.common.util.offerOrRemove
import com.myStash.core.di.IoDispatcher
import com.myStash.core.model.Image
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
    private val imageRepository: ImageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ContainerHost<GalleryScreenState, GallerySideEffect>, ViewModel() {

    override val container: Container<GalleryScreenState, GallerySideEffect> =
        container(GalleryScreenState())

    init {
        fetch()
        initGalleryImages()
    }

    private val _selectedList = mutableListOf<Image>()
    val selectedList get() = _selectedList.toList()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                imageRepository.imagesStateFlow.collectLatest {
                    reduce {
                        state.copy(imageList = it)
                    }
                }
            }
        }
    }

    private fun initGalleryImages() {
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

    fun event(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.OnClick -> {
                selectImage(event.image)
            }
            is GalleryEvent.Zoom -> {
                zoomImage(event.image)
            }
        }
    }

    private fun selectImage(image: Image) {
        intent {
            _selectedList.offerOrRemove(image) { it.name == image.name }
            reduce {
                state.copy(
                    selectedImageList = _selectedList.toList()
                )
            }
        }
    }

    private fun zoomImage(image: Image) {
        intent {
            postSideEffect(GallerySideEffect.Zoom(image))
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageRepository.cleanup()
    }
}

sealed interface GalleryEvent {
    data class OnClick(val image: Image): GalleryEvent
    data class Zoom(val image: Image): GalleryEvent
}