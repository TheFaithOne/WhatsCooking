package one.vitaliy.whatscooking.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class WhatsCookingTypography(
    val display: DisplayTypography,
    val headline: HeadlineTypography,
    val title: TitleTypography,
    val body: BodyTypography,
    val label: LabelTypography,
)

@Immutable
data class DisplayTypography(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)

@Immutable
data class HeadlineTypography(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)

@Immutable
data class TitleTypography(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)

@Immutable
data class BodyTypography(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)

@Immutable
data class LabelTypography(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)
