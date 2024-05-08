package com.myStash.feature.gallery

import androidx.lifecycle.ViewModel
import com.myStash.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(

): ContainerHost<GalleryScreenState, GallerySideEffect>, ViewModel() {

    override val container: Container<GalleryScreenState, GallerySideEffect> =
        container(GalleryScreenState())

    private var selectedList: List<Image> = emptyList()

    fun setImage(images: List<Image>) {
        intent {
            reduce {
                state.copy(imageList = images)
            }
        }
    }

    fun isSelected(image: Image) {
        intent {
            val newList = selectedList.toMutableList().apply {
                val index = indexOfFirst { it.name == image.name }
                if(index != -1) {
                    removeAt(index)
                } else {
                    add(image)
                }
            }

            selectedList = newList.toList()

            reduce {
                state.copy(
                    selectedImageList = newList
                )
            }
        }
    }
}