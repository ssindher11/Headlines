package com.ssindher.headlines.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.ui.details.NewsDetailsScreen
import com.ssindher.headlines.ui.home.HomeScreen
import com.ssindher.headlines.ui.home.HomeViewModel
import com.ssindher.headlines.ui.splash.AnimatedSplashScreen
import com.ssindher.headlines.util.AssetParamType

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    isNetworkAvailable: Boolean,
    homeViewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(isNetworkAvailable = isNetworkAvailable, viewModel = homeViewModel) {
                val article = Uri.encode(Gson().toJson(it))
                navController.navigate("${Screen.Details.route}/$article")
            }
        }
        composable(
            route = "${Screen.Details.route}/{article}",
            arguments = listOf(navArgument("article") { type = AssetParamType() })
        ) {
            val article = it.arguments?.getParcelable<NewsArticle>("article")
            NewsDetailsScreen(article) { navController.navigateUp() }
        }
    }
}