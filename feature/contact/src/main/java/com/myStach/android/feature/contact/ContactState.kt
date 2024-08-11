package com.myStach.android.feature.contact

data class ContactState(
    val typeList: List<String> = emptyList(),
    var selectedType: String? = null,
    var selectedImages: List<String> = emptyList(),
    var content: String = "",
    val isCompleted: Boolean = false
)

sealed interface ContactSideEffect {
    object Finish: ContactSideEffect
}

sealed interface ContactAction {
    data class SelectType(val type: String) : ContactAction
    data class ChangeContent(val content: String) : ContactAction
    data class ChangeImages(val images: List<String>) : ContactAction
    object ShowGalleryActivity: ContactAction
    object DeleteEmail: ContactAction
    object Confirm: ContactAction
    object Finish: ContactAction

}