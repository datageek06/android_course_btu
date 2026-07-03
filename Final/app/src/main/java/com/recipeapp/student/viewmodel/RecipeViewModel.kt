package com.recipeapp.student.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.recipeapp.student.model.Chef
import com.recipeapp.student.model.Recipe
import com.recipeapp.student.repository.FirebaseRepository

class RecipeViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    // Expose plain states for Jetpack Compose observers
    var chefs = mutableStateOf<List<Chef>>(emptyList())

    var recipes = mutableStateOf<List<Recipe>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        repository.getChefs { chefs.value = it }
        repository.getRecipes { recipes.value = it }
    }

    fun toggleFollowChef(chef: Chef) {
        repository.toggleFollowChef(chef.id, chef.isFollowed)
    }

    fun toggleLikeRecipe(recipe: Recipe) {
        repository.toggleLikeRecipe(recipe.id, recipe.isLiked)
    }
}