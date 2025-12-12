package one.vitaliy.whatscooking.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_meal")
data class FavouriteMealEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String,
)
