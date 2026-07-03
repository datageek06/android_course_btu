package com.recipeapp.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipeapp.student.model.Recipe
import com.recipeapp.student.viewmodel.RecipeViewModel

@Composable
fun RecipeItemCard(
    recipe: Recipe,
    viewModel: RecipeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val chefName = viewModel.chefs.value.find { it.id == recipe.chefId?.id }?.name ?: "Unknown Chef"

    Box(
        modifier = modifier
            .width(170.dp)
            .height(190.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { navController.navigate("recipe_detail/${recipe.id}") }
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // გრადიენტი, იმისათვის რომ სათაური უკეთ გამოჩნდეს
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { viewModel.toggleLikeRecipe(recipe) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = if (recipe.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (recipe.isLiked) Color.Red else Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                text = recipe.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = chefName,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}