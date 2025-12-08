package one.vitaliy.whatscooking.categories.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.categories.CategoriesViewModel
import one.vitaliy.whatscooking.categories.CategoryUiState
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.error_generic
import whatscooking.composeapp.generated.resources.error_retry

@Serializable
object CategoriesPage

@Composable
fun CategoriesPage(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = uiState,
        modifier = modifier.fillMaxSize().padding(paddingValues),
    ) { state ->
        when (state) {
            is CategoryUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize(),
            )

            is CategoryUiState.Content -> CategoriesContent(
                state = state,
                onCategoryClick = onCategoryClick,
            )

            is CategoryUiState.Error -> CategoriesError(
                throwable = state.throwable,
                onRetry = viewModel::refresh,
            )
        }
    }
}

@Composable
private fun CategoriesContent(
    state: CategoryUiState.Content,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = state.categories, key = { it.idCategory.orEmpty() }) { category ->
            CategoryItem(
                categoryName = category.strCategory.orEmpty(),
                categoryImage = category.strCategoryThumb.orEmpty(),
                description = category.strCategoryDescription.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onCategoryClick(category.idCategory.orEmpty()) },
                    ),
            )
        }
    }
}

@Composable
private fun CategoryItem(
    categoryName: String,
    categoryImage: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = categoryName,
            style = WhatsCookingTheme.typography.headline.medium,
        )
        AsyncImage(
            model = categoryImage,
            contentDescription = categoryName,
        )
        Text(
            text = description,
            style = WhatsCookingTheme.typography.body.medium,
        )
    }
}

@Composable
private fun CategoriesError(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.error_generic, throwable.message.orEmpty()),
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRetry,
        ) {
            Text(stringResource(Res.string.error_retry))
        }
    }
}
