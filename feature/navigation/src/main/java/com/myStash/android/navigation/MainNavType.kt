package com.myStash.android.navigation

import com.myStash.android.feature.navigation.R

enum class MainNavType(
    val activeIcon: Int,
    val inactiveIcon: Int,
) {
    HAS(
        activeIcon = R.drawable.btn_btm_bar_swipe_on,
        inactiveIcon = R.drawable.btn_btm_bar_swipe_off
    ),
    STYLE(
        activeIcon = R.drawable.btn_btm_bar_like_on,
        inactiveIcon = R.drawable.btn_btm_bar_like_off
    ),
    FEED(
        activeIcon = R.drawable.btn_btm_bar_message_on,
        inactiveIcon = R.drawable.btn_btm_bar_message_off
    ),
    MY_PAGE(
        activeIcon = R.drawable.btn_btm_bar_my_page_on,
        inactiveIcon = R.drawable.btn_btm_bar_my_page_off
    )
}