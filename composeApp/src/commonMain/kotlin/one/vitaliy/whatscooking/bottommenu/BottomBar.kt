package one.vitaliy.whatscooking.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun NavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = modifier.fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MainNavigationPill(
                selectedRoute = selectedRoute,
                onItemClick = { navController.navigate(it.route) },
            )
            SeparateRandomAction(
                selected = selectedRoute == BottomBarDestination.RANDOM.route::class.qualifiedName,
                onClick = { navController.navigate(BottomBarDestination.RANDOM.route) },
            )
        }
    }
}

@Composable
private fun MainNavigationPill(
    selectedRoute: String?,
    onItemClick: (BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(32.dp),
        color = WhatsCookingTheme.colors.background.cardElevated,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomBarDestination.mainDestinations.forEach { item ->
                NavItem(
                    item = item,
                    selected = selectedRoute == item.route::class.qualifiedName,
                    modifier = Modifier.clickable { onItemClick(item) },
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    item: BottomBarDestination,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .background(
                if (selected) {
                    WhatsCookingTheme.colors.brand.accent.copy(alpha = 0.18f)
                } else {
                    Color.Transparent
                },
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
    ) {
        Icon(
            painter = painterResource(if (selected) item.filledIconRes else item.outlineIconRes),
            contentDescription = item.title,
            tint = if (selected) {
                WhatsCookingTheme.colors.brand.accent
            } else {
                WhatsCookingTheme.colors.elements.icon
            },
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = item.title,
            style = WhatsCookingTheme.typography.label.medium,
            color = if (selected) {
                WhatsCookingTheme.colors.typography.headline
            } else {
                WhatsCookingTheme.colors.typography.bodySecondary
            },
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SeparateRandomAction(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(56.dp).clickable(onClick = onClick),
        shape = CircleShape,
        color = if (selected) {
            WhatsCookingTheme.colors.brand.accent
        } else {
            WhatsCookingTheme.colors.background.cardElevated
        },
        shadowElevation = 8.dp,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(
                    if (selected) {
                        BottomBarDestination.RANDOM.filledIconRes
                    } else {
                        BottomBarDestination.RANDOM.outlineIconRes
                    },
                ),
                contentDescription = BottomBarDestination.RANDOM.title,
                tint = if (selected) {
                    WhatsCookingTheme.colors.typography.onAccent
                } else {
                    WhatsCookingTheme.colors.elements.icon
                },
            )
        }
    }
}

@Preview
@Composable
private fun NavItemPreview() {
    PreviewTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NavItem(item = BottomBarDestination.HOME, selected = true)
            NavItem(item = BottomBarDestination.FAVOURITES, selected = false)
            NavItem(item = BottomBarDestination.SEARCH, selected = false)
            NavItem(item = BottomBarDestination.RANDOM, selected = false)
        }
    }
}
