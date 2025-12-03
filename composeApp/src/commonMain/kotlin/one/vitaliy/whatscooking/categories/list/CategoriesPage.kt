package one.vitaliy.whatscooking.categories.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.categories.CategoriesViewModel
import org.koin.compose.viewmodel.koinViewModel

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
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = uiState?.categories.orEmpty(), key = { it.idCategory.orEmpty() }) {
            CategoryItem(
                categoryName = it.strCategory.orEmpty(),
                categoryImage = it.strCategoryThumb.orEmpty(),
                description = it.strCategoryDescription.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onCategoryClick(it.idCategory.orEmpty()) }
                    )
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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = categoryName, style = MaterialTheme.typography.headlineMedium)
        AsyncImage(model = categoryImage, contentDescription = null)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}