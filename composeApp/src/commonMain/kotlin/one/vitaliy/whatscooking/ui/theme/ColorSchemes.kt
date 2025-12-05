package one.vitaliy.whatscooking.ui.theme

import androidx.compose.ui.graphics.Color

val lightColors = WhatsCookingColors(
    brand = BrandColors(
        primary = DustyMauve,
        primarySecondary = MauveShadow,
        primaryContent = Color.White,
        accent = AlmondSilk,
        accentSecondary = MauveShadow,
        accentContent = ShadowGrey,
    ),
    background = BackgroundColors(
        page = Color(0xFFFFFBFF),
        surface = Color.White,
        surfaceVariant = AlmondSilk,
        modal = Color.White,
        modalScrim = Color.Black.copy(alpha = 0.5f),
        card = Color.White,
        cardElevated = Color.White,
    ),
    typography = TypographyColors(
        headline = ShadowGrey,
        headlineHighlighted = DustyMauve,
        body = ShadowGrey,
        bodySecondary = MidnightViolet,
        bodyDisabled = MauveShadow.copy(alpha = 0.6f),
        onPrimary = Color.White,
        onAccent = ShadowGrey,
    ),
    elements = ElementsColors(
        divider = AlmondSilk,
        border = MauveShadow,
        icon = MidnightViolet,
        iconContrast = DustyMauve,
        badge = BadgeColors(
            background = DustyMauve,
            content = Color.White,
        ),
    ),
    system = SystemColors(
        success = StateColors(
            background = Color(0xFFD4EDDA),
            backgroundSecondary = Color(0xFFC3E6CB),
            content = Color(0xFF155724),
            border = Color(0xFF28A745),
        ),
        warning = StateColors(
            background = Color(0xFFFFF3CD),
            backgroundSecondary = Color(0xFFFFECAB),
            content = Color(0xFF856404),
            border = Color(0xFFFFC107),
        ),
        error = StateColors(
            background = Color(0xFFF8D7DA),
            backgroundSecondary = Color(0xFFF5C6CB),
            content = Color(0xFF721C24),
            border = Color(0xFFDC3545),
        ),
        info = StateColors(
            background = Color(0xFFD1ECF1),
            backgroundSecondary = Color(0xFFBEE5EB),
            content = Color(0xFF0C5460),
            border = Color(0xFF17A2B8),
        ),
    ),
    interactions = InteractionsColors(
        primary = InteractionStateColors(
            default = DustyMauve,
            hover = MauveShadow,
            active = MidnightViolet,
            disabled = MauveShadow.copy(alpha = 0.4f),
            content = Color.White,
            contentDisabled = Color.White.copy(alpha = 0.6f),
        ),
        secondary = InteractionStateColors(
            default = AlmondSilk,
            hover = MauveShadow.copy(alpha = 0.2f),
            active = MauveShadow,
            disabled = AlmondSilk.copy(alpha = 0.4f),
            content = ShadowGrey,
            contentDisabled = ShadowGrey.copy(alpha = 0.4f),
        ),
        tertiary = InteractionStateColors(
            default = Color.Transparent,
            hover = AlmondSilk.copy(alpha = 0.3f),
            active = AlmondSilk.copy(alpha = 0.5f),
            disabled = Color.Transparent,
            content = DustyMauve,
            contentDisabled = MauveShadow.copy(alpha = 0.4f),
        ),
    ),
)

val darkColors = WhatsCookingColors(
    brand = BrandColors(
        primary = AlmondSilk,
        primarySecondary = DustyMauve,
        primaryContent = MidnightViolet,
        accent = DustyMauve,
        accentSecondary = MauveShadow,
        accentContent = AlmondSilk,
    ),
    background = BackgroundColors(
        page = ShadowGrey,
        surface = MidnightViolet,
        surfaceVariant = MauveShadow,
        modal = MidnightViolet,
        modalScrim = Color.Black.copy(alpha = 0.7f),
        card = MidnightViolet,
        cardElevated = MauveShadow,
    ),
    typography = TypographyColors(
        headline = AlmondSilk,
        headlineHighlighted = DustyMauve,
        body = AlmondSilk,
        bodySecondary = AlmondSilk.copy(alpha = 0.8f),
        bodyDisabled = AlmondSilk.copy(alpha = 0.4f),
        onPrimary = MidnightViolet,
        onAccent = ShadowGrey,
    ),
    elements = ElementsColors(
        divider = MauveShadow,
        border = MauveShadow,
        icon = AlmondSilk,
        iconContrast = DustyMauve,
        badge = BadgeColors(
            background = DustyMauve,
            content = AlmondSilk,
        ),
    ),
    system = SystemColors(
        success = StateColors(
            background = Color(0xFF155724),
            backgroundSecondary = Color(0xFF1E7E34),
            content = Color(0xFFD4EDDA),
            border = Color(0xFF28A745),
        ),
        warning = StateColors(
            background = Color(0xFF856404),
            backgroundSecondary = Color(0xFF9C6F06),
            content = Color(0xFFFFF3CD),
            border = Color(0xFFFFC107),
        ),
        error = StateColors(
            background = Color(0xFF721C24),
            backgroundSecondary = Color(0xFF8B2530),
            content = Color(0xFFF8D7DA),
            border = Color(0xFFDC3545),
        ),
        info = StateColors(
            background = Color(0xFF0C5460),
            backgroundSecondary = Color(0xFF136A77),
            content = Color(0xFFD1ECF1),
            border = Color(0xFF17A2B8),
        ),
    ),
    interactions = InteractionsColors(
        primary = InteractionStateColors(
            default = AlmondSilk,
            hover = DustyMauve,
            active = MauveShadow,
            disabled = AlmondSilk.copy(alpha = 0.3f),
            content = MidnightViolet,
            contentDisabled = MidnightViolet.copy(alpha = 0.4f),
        ),
        secondary = InteractionStateColors(
            default = DustyMauve,
            hover = MauveShadow,
            active = MidnightViolet,
            disabled = DustyMauve.copy(alpha = 0.3f),
            content = AlmondSilk,
            contentDisabled = AlmondSilk.copy(alpha = 0.4f),
        ),
        tertiary = InteractionStateColors(
            default = Color.Transparent,
            hover = MauveShadow.copy(alpha = 0.3f),
            active = MauveShadow.copy(alpha = 0.5f),
            disabled = Color.Transparent,
            content = AlmondSilk,
            contentDisabled = AlmondSilk.copy(alpha = 0.4f),
        ),
    ),
)
