package com.recipeapp.student.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipeapp.student.viewmodel.RecipeViewModel

@Composable
fun ChefDetailScreen(
    chefId: String,
    viewModel: RecipeViewModel,
    navController: NavController
) {
    val chef = viewModel.chefs.value.find { it.id == chefId } ?: return
    val chefRecipes = viewModel.recipes.value.filter { it.chefId?.id == chefId }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    FloatingCircleButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow",
                            modifier = Modifier.size(22.dp),
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = chef.imageUrl,
                        contentDescription = chef.name,
                        modifier = Modifier.size(88.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { viewModel.toggleFollowChef(chef) },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5E3A)),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(text = if (chef.isFollowed) "Following" else "Follow")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = chef.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(text = chef.bio, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Recipes by ${chef.name}", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        items(chefRecipes) { recipe ->
            RecipeItemCard(
                recipe = recipe,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}