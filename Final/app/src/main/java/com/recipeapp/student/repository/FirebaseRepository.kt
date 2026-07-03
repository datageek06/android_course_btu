package com.recipeapp.student.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.recipeapp.student.model.Chef
import com.recipeapp.student.model.Recipe

class FirebaseRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getChefs(onResult: (List<Chef>) -> Unit) {
        firestore.collection("chefs")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val chefsList = mutableListOf<Chef>()
                snapshot?.let {
                    for (document in it.documents) {
                        val chef = document.toObject(Chef::class.java)
                        if (chef != null) {
                            chef.id = document.id
                            chefsList.add(chef)
                        }
                    }
                }
                onResult(chefsList)
            }
    }

    fun getRecipes(onResult: (List<Recipe>) -> Unit) {
        firestore.collection("recipes")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val recipesList = mutableListOf<Recipe>()
                snapshot?.let {
                    for (document in it.documents) {
                        val recipe = document.toObject(Recipe::class.java)
                        if (recipe != null) {
                            recipe.id = document.id
                            recipesList.add(recipe)
                        }
                    }
                }
                onResult(recipesList)
            }
    }

    fun toggleFollowChef(chefId: String, currentStatus: Boolean) {
        firestore.collection("chefs").document(chefId).update("isFollowed", !currentStatus)
    }

    fun toggleLikeRecipe(recipeId: String, currentStatus: Boolean) {
        firestore.collection("recipes").document(recipeId).update("isLiked", !currentStatus)
    }
}