package com.myStash.android.navigation

import com.myStash.android.common.resource.R

enum class MainNavType(
    val lightActiveIcon: Int,
    val lightInactiveIcon: Int,
    val darkActiveIcon: Int,
    val darkInactiveIcon: Int,
) {
    HAS(
        lightActiveIcon = R.drawable.img_nav_has_light_on,
        lightInactiveIcon = R.drawable.img_nav_has_light_off,
        darkActiveIcon = R.drawable.img_nav_has_dark_on,
        darkInactiveIcon = R.drawable.img_nav_has_dark_off
    ),
    STYLE(
        lightActiveIcon = R.drawable.img_nav_style_light_on,
        lightInactiveIcon = R.drawable.img_nav_style_light_off,
        darkActiveIcon = R.drawable.img_nav_style_dark_on,
        darkInactiveIcon = R.drawable.img_nav_style_dark_off
    ),
    FEED(
        lightActiveIcon = R.drawable.img_nav_feed_light_on,
        lightInactiveIcon = R.drawable.img_nav_feed_light_off,
        darkActiveIcon = R.drawable.img_nav_feed_dark_on,
        darkInactiveIcon = R.drawable.img_nav_feed_dark_off
    ),
    MY_PAGE(
        lightActiveIcon = R.drawable.img_nav_mypage_light_on,
        lightInactiveIcon = R.drawable.img_nav_mypage_light_off,
        darkActiveIcon = R.drawable.img_nav_mypage_dark_on,
        darkInactiveIcon = R.drawable.img_nav_mypage_dark_off
    )
}