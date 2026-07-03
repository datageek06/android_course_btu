package com.recipeapp.student.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    viewModel: RecipeViewModel,
    navController: NavController
) {
    val recipe = viewModel.recipes.value.find { it.id == recipeId } ?: return
    val chef = viewModel.chefs.value.find { it.id == recipe.chefId?.id }

    val context = LocalContext.current

    var selectedTab by remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingCircleButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Arrow",
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )
                }

                FloatingCircleButton(onClick = { viewModel.toggleLikeRecipe(recipe) }) {
                    Icon(
                        imageVector = if (recipe.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (recipe.isLiked) Color.Red else Color.DarkGray,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = recipe.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                IconButton(onClick = { shareRecipe(context, recipe.title) }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Recipe"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${recipe.servings} servings", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(14.dp))

            if (chef != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("chef_detail/${chef.id}") }
                        .padding(vertical = 4.dp)
                ) {
                    AsyncImage(
                        model = chef.imageUrl,
                        contentDescription = chef.name,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = chef.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.toggleFollowChef(chef) },
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color(0xFFFF5E3A)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF5E3A)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(text = if (chef.isFollowed) "Following" else "+ Follow", fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SegmentedTab("Ingredients", selectedTab == 0, Modifier.weight(1f)) { selectedTab = 0 }
                SegmentedTab("Instructions", selectedTab == 1, Modifier.weight(1f)) { selectedTab = 1 }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (selectedTab) {
                0 -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    recipe.ingredients.forEach { ingredient ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "•", color = Color(0xFFFF5E3A), fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = ingredient, fontSize = 14.sp)
                        }
                    }
                }
                1 -> Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    recipe.steps.forEachIndexed { index, step ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Step ${index + 1}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFFF5E3A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step,
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FloatingCircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun SegmentedTab(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Color(0xFFFF5E3A) else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = if (selected) Color.White else Color.Gray,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

fun shareRecipe(context: Context, recipeTitle: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, "What a great recipe!")
        putExtra(Intent.EXTRA_TEXT, "Today I'm preparing: $recipeTitle.")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share recipe:")
    context.startActivity(shareIntent)
}