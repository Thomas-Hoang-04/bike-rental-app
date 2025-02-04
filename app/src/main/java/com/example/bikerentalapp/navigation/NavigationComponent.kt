package com.example.bikerentalapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified T : Any> NavGraphBuilder.horizontalNavigation(
    crossinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(400)
            )
        }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}

inline fun <reified T : Any> NavGraphBuilder.verticalNavigation(
    crossinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(400)
            )
        }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}
