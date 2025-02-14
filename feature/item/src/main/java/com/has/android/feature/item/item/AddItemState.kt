package com.has.android.feature.item.item

data class AddItemState(
    val selectedTab: ItemTab = ItemTab.HAS,
    val editTab: ItemTab? = null
)

sealed interface AddItemSideEffect