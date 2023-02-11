package com.amalhanaja.movietrek.core.designsystem

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@VisibleForTesting
val DarkColorScheme = darkColorScheme()

@VisibleForTesting
val LightColorScheme = lightColorScheme()

@Composable
fun MovieTrekTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicTheme: Boolean = isSupportDynamicTheme(),
    spacings: Spacings = Spacings(),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current
    val colorScheme = when {
        dynamicTheme && darkTheme -> dynamicDarkColorScheme(context)
        dynamicTheme -> dynamicLightColorScheme(context)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    if (!view.isInEditMode) {
        SideEffect {
            val activity = context.findActivity()
            val windowInsetsController = WindowCompat.getInsetsController(activity.window, view)
            windowInsetsController.isAppearanceLightStatusBars = darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = darkTheme
            activity.window.statusBarColor = colorScheme.primary.toArgb()
            activity.window.navigationBarColor = colorScheme.primary.toArgb()
        }
    }
    CompositionLocalProvider(
        LocalSpacings provides spacings
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MovieTrekTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isSupportDynamicTheme(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}