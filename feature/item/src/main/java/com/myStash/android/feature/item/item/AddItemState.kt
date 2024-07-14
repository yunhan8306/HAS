package com.myStash.android.feature.item.item

data class AddItemState(
    val selectedTab: ItemTab = ItemTab.HAS
)

sealed interface AddItemSideEffect