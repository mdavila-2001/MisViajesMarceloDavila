package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.NavRoutes
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.LoginScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.SplashScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.TripsScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.SplashViewModel

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(
                navController = navController,
            )
        }
        composable(NavRoutes.Login.route) {
            LoginScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(NavRoutes.Trips.route) {
            TripsScreen(
                navController = navController
            )
        }
    }
}