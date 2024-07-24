package com.myStash.android.feature.item.item

enum class ItemTab(
    val tabName: String
) {
    HAS("HAS"), STYLE("STYLE"), FEED("FEED")
}

fun String?.getItemTab(): ItemTab {
    return when(this) {
        ItemTab.HAS.tabName -> ItemTab.HAS
        ItemTab.STYLE.tabName -> ItemTab.STYLE
        ItemTab.FEED.tabName -> ItemTab.FEED
        else -> ItemTab.HAS
    }
}