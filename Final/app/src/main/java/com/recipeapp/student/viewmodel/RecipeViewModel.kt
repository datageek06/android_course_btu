package com.recipeapp.student.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.recipeapp.student.model.Chef
import com.recipeapp.student.model.Recipe
import com.recipeapp.student.firebase.FireStore

class RecipeViewModel : ViewModel() {
    private val firestore = FireStore()

    // Expose plain states for Jetpack Compose observers
    var chefs = mutableStateOf<List<Chef>>(emptyList())

    var recipes = mutableStateOf<List<Recipe>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        firestore.getChefs { chefs.value = it }
        firestore.getRecipes { recipes.value = it }
    }

    fun toggleFollowChef(chef: Chef) {
        firestore.toggleFollowChef(chef.id, chef.isFollowed)
    }

    fun toggleLikeRecipe(recipe: Recipe) {
        firestore.toggleLikeRecipe(recipe.id, recipe.isLiked)
    }
}
