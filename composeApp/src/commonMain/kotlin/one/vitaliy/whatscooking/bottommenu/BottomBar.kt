package one.vitaliy.whatscooking.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    NavigationBar(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomBarDestination.entries.forEach {
                val selected =
                    navBackStackEntry?.destination?.route == it.route::class.qualifiedName
                IconButton(
                    onClick = {
                        navController.navigate(it.route)
                    },
                    modifier = Modifier.weight(1f).background(
                        if (selected) {
                            WhatsCookingTheme.colors.brand.accent
                        } else {
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(16.dp),
                    ),
                ) {
                    NavItem(
                        item = it,
                        selected = selected,
                    )
                }
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
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(if (selected) item.filledIconRes else item.outlineIconRes),
            contentDescription = item.title,
        )
        Text(
            text = item.title,
            style = WhatsCookingTheme.typography.label.medium,
            color = WhatsCookingTheme.colors.typography.body,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun NavItemPreview() {
    PreviewTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NavItem(item = BottomBarDestination.HOME, selected = true)
            NavItem(item = BottomBarDestination.RANDOM, selected = false)
        }
    }
}
