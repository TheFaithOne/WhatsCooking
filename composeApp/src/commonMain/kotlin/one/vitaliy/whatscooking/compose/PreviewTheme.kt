package one.vitaliy.whatscooking.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme

@Composable
fun PreviewTheme(
    surfaceColorProvider: @Composable () -> Color = {
        WhatsCookingTheme.colors.background.surface
    },
    content: @Composable () -> Unit,
) {
    WhatsCookingTheme(
        darkTheme = isSystemInDarkTheme(),
    ) {
        Surface(color = surfaceColorProvider()) {
            content()
        }
    }
}
