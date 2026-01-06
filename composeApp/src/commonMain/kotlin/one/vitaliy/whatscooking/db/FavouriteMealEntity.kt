package one.vitaliy.whatscooking.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import one.vitaliy.whatscooking.data.MealDomain

@Entity(tableName = "favourite_meal")
data class FavouriteMealEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String,
    val thumbnailUrl: String,
    val category: String,
    val ingredientCount: Int,
)

fun FavouriteMealEntity.toDomain() = MealDomain(
    id = id,
    name = name,
    instructions = "",
    thumbnailUrl = thumbnailUrl,
    youtubeUrl = null,
    category = category,
    origin = region,
    ingredientsWithMeasures = emptyMap(),
    isFavourite = true,
)
