package com.amalhanaja.movietrek.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal object SpacingTokens {
    val Xs: Dp = 4.dp
    val S: Dp = 8.dp
    val M: Dp = 12.dp
    val L: Dp = 16.dp
    val Xl: Dp = 20.dp
    val Xxl: Dp = 24.dp
    val Jumbo = 32.dp
    val Mega = 48.dp
    val Giant: Dp = 64.dp
}

@Immutable
data class Spacings(
    val xs: Dp = SpacingTokens.Xs,
    val s: Dp = SpacingTokens.S,
    val m: Dp = SpacingTokens.M,
    val l: Dp = SpacingTokens.L,
    val xl: Dp = SpacingTokens.Xl,
    val xxl: Dp = SpacingTokens.Xxl,
    val jumbo: Dp = SpacingTokens.Jumbo,
    val mega: Dp = SpacingTokens.Mega,
    val giant: Dp = SpacingTokens.Giant,
)

internal val LocalSpacings = staticCompositionLocalOf { Spacings() }