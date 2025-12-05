package one.vitaliy.whatscooking.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class WhatsCookingColors(
    val brand: BrandColors,
    val background: BackgroundColors,
    val typography: TypographyColors,
    val elements: ElementsColors,
    val system: SystemColors,
    val interactions: InteractionsColors,
)

@Immutable
data class BrandColors(
    val primary: Color,
    val primarySecondary: Color,
    val primaryContent: Color,
    val accent: Color,
    val accentSecondary: Color,
    val accentContent: Color,
)

@Immutable
data class BackgroundColors(
    val page: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val modal: Color,
    val modalScrim: Color,
    val card: Color,
    val cardElevated: Color,
)

@Immutable
data class TypographyColors(
    val headline: Color,
    val headlineHighlighted: Color,
    val body: Color,
    val bodySecondary: Color,
    val bodyDisabled: Color,
    val onPrimary: Color,
    val onAccent: Color,
)

@Immutable
data class ElementsColors(
    val divider: Color,
    val border: Color,
    val icon: Color,
    val iconContrast: Color,
    val badge: BadgeColors,
)

@Immutable
data class BadgeColors(
    val background: Color,
    val content: Color,
)

@Immutable
data class SystemColors(
    val success: StateColors,
    val warning: StateColors,
    val error: StateColors,
    val info: StateColors,
)

@Immutable
data class StateColors(
    val background: Color,
    val backgroundSecondary: Color,
    val content: Color,
    val border: Color,
)

@Immutable
data class InteractionsColors(
    val primary: InteractionStateColors,
    val secondary: InteractionStateColors,
    val tertiary: InteractionStateColors,
)

@Immutable
data class InteractionStateColors(
    val default: Color,
    val hover: Color,
    val active: Color,
    val disabled: Color,
    val content: Color,
    val contentDisabled: Color,
)
