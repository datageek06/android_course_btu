package com.recipeapp.student

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.recipeapp.student.screens.*
import com.recipeapp.student.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) { paddingValues ->
                NavigationGraph(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Explore : Screen("explore", "Explore")
    object Saved : Screen("saved", "Saved")
    object Menu : Screen("menu", "Menu")
    object ChefDetail : Screen("chef_detail/{chefId}", "Chef Detail")
    object RecipeDetail : Screen("recipe_detail/{recipeId}", "Recipe Detail")
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Explore, Screen.Saved, Screen.Menu)
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            val icon = when (screen) {
                Screen.Home -> Icons.Default.Home
                Screen.Explore -> Icons.Default.Search
                Screen.Saved -> Icons.Default.Favorite
                Screen.Menu -> Icons.Default.Menu
                else -> Icons.Default.Home
            }

            NavigationBarItem(
                icon = { Icon(icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute?.startsWith(screen.route.split("/")[0]) == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(viewModel, navController)
        }
        composable(Screen.Explore.route) {
            ExploreScreen(navController, viewModel)
        }
        composable(Screen.Saved.route) {
            SavedScreen(navController, viewModel)
        }
        composable(Screen.Menu.route) {
            MenuScreen()
        }
        composable(Screen.ChefDetail.route) { backStackEntry ->
            val chefId = backStackEntry.arguments?.getString("chefId") ?: ""
            ChefDetailScreen(
                chefId = chefId,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Screen.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeDetailScreen(recipeId = recipeId, viewModel = viewModel, navController = navController)
        }
    }
}