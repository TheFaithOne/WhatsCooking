package one.vitaliy.whatscooking.recipes

import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.networking.ApiNinjasApiServices
import one.vitaliy.whatscooking.networking.RecipeDto
import one.vitaliy.whatscooking.networking.RecipeIngredientDto
import kotlin.random.Random

interface RecipeRepository {
    suspend fun searchRecipes(query: String): List<RecipeDomain>
    suspend fun getRandomRecipe(): RecipeDomain
    suspend fun getRecipe(id: String): RecipeDomain
}

class ApiNinjasRecipeRepository(
    private val apiServices: ApiNinjasApiServices,
) : RecipeRepository {

    private companion object {
        val RANDOM_RECIPE_QUERIES = listOf(
            "chicken",
            "turkey",
            "pasta",
            "rice",
            "soup",
            "salad",
            "beef",
            "pork",
            "lamb",
            "fish",
            "salmon",
            "tuna",
            "vegetarian",
            "vegan",
            "potato",
            "sweet potato",
            "curry",
            "beans",
            "lentils",
            "chickpeas",
            "noodles",
            "tacos",
            "burrito",
            "quesadilla",
            "stew",
            "chili",
            "shrimp",
            "mushroom",
            "cheese",
            "tomato",
            "eggplant",
            "zucchini",
            "broccoli",
            "cauliflower",
            "spinach",
            "kale",
            "quinoa",
            "couscous",
            "barley",
            "stir fry",
            "roast",
            "casserole",
            "skillet",
            "bowl",
            "wrap",
            "sandwich",
            "burger",
            "meatballs",
            "sausage",
            "meatloaf",
            "risotto",
            "gnocchi",
            "ramen",
            "pho",
            "pad thai",
            "fried rice",
            "bibimbap",
            "paella",
            "lasagna",
            "enchiladas",
            "fajitas",
            "shawarma",
            "falafel",
            "kebab",
            "gyros",
            "teriyaki",
            "meat",
            "seafood",
            "tofu",
        )
    }

    override suspend fun searchRecipes(query: String): List<RecipeDomain> {
        return apiServices.getRecipes(query).map { it.toDomain() }
    }

    override suspend fun getRandomRecipe(): RecipeDomain {
        val query = RANDOM_RECIPE_QUERIES.random()
        return searchRecipes(query).randomOrNull(Random)
            ?: error("No recipe found for query: $query")
    }

    override suspend fun getRecipe(id: String): RecipeDomain {
        return searchRecipes(id).firstOrNull { it.id == id || it.title.equals(id, ignoreCase = true) }
            ?: searchRecipes(id).firstOrNull()
            ?: error("No recipe found for: $id")
    }
}

private fun RecipeDto.toDomain(): RecipeDomain {
    return RecipeDomain(
        id = title,
        title = title,
        ingredients = ingredients.map { it.toDisplayText() },
        servings = servings,
        instructions = instructions.joinToString(separator = "\n\n"),
    )
}

private fun RecipeIngredientDto.toDisplayText(): String {
    return listOfNotNull(
        quantity?.takeUnless { it == 0.0 }?.toDisplayText(),
        unit?.takeUnless { it.isBlank() || it == "unit" },
        name,
    ).joinToString(separator = " ")
}

private fun Double.toDisplayText(): String =
    if (rem(1.0) == 0.0) toInt().toString() else toString()
