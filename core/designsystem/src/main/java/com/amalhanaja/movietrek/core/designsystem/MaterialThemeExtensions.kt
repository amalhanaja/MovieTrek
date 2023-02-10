package com.amalhanaja.movietrek.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Used for get current spacings system to extends MaterialTheme design system
 */
@Suppress("unused")
val MaterialTheme.spacings: Spacings
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacings.current