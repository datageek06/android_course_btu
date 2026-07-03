package com.recipeapp.student.model

import com.google.firebase.firestore.PropertyName

data class Chef(
    var id: String = "",
    val name: String = "",
    val bio: String = "",
    val rating: Double = 0.0,
    val imageUrl: String = "",

    @get:PropertyName("isFollowed")
    @set:PropertyName("isFollowed")
    @field:PropertyName("isFollowed")
    var isFollowed: Boolean = false
)