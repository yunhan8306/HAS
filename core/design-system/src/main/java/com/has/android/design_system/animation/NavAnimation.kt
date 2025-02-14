package com.has.android.design_system.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

val AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionStart: EnterTransition?
    get() = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween()
    )

val AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionEnd: EnterTransition?
    get() = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween()
    )

val AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionStart: ExitTransition?
    get() = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween()
    )

val AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionEnd: ExitTransition?
    get() = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween()
    )