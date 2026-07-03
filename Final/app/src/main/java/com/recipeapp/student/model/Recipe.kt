package com.recipeapp.student.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class Recipe(
    var id: String = "",
    var chefId: DocumentReference? = null,
    val title: String = "",
    val imageUrl: String = "",
    val servings: Int = 1,
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),

    @get:PropertyName("isLiked")
    @set:PropertyName("isLiked")
    @field:PropertyName("isLiked")
    var isLiked: Boolean = false
)