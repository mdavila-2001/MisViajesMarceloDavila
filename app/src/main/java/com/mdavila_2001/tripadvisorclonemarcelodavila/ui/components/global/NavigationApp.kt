package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.NavRoutes
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.LoginScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.PlaceDetailScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.PlaceFormScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.SplashScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.TripDetailScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.TripFormScreen
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens.TripsScreen

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
        composable(
            route = NavRoutes.TripForm.route,
            arguments = NavRoutes.TripForm.arguments
        ) { backStackEntry ->

            val tripId = backStackEntry.arguments?.getInt("tripId")
            val name = backStackEntry.arguments?.getString("name")
            val country = backStackEntry.arguments?.getString("country")

            val decodedName = name?.replace("_", " ")
            val decodedCountry = country?.replace("_", " ")

            TripFormScreen(
                navController = navController,
                tripId = if (tripId == -1) null else tripId,
                name = decodedName,
                country = decodedCountry
            )
        }
        composable(
            NavRoutes.TripDetail.route,
            arguments = NavRoutes.TripDetail.arguments
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getInt("tripId")
            val tripName = backStackEntry.arguments?.getString("tripName")
            val tripOwner = backStackEntry.arguments?.getString("tripOwner")

            requireNotNull(tripId) { "El ID del viaje es nulo" }
            requireNotNull(tripName) { "El Nombre del viaje es nulo" }
            requireNotNull(tripOwner) { "El Dueño del viaje es nulo" }

            TripDetailScreen(
                navController = navController,
                tripId = tripId,
                tripName = tripName.replace("_", " "),
                tripOwner = tripOwner
            )
        }

        composable(
            NavRoutes.PlaceDetail.route,
            arguments = NavRoutes.PlaceDetail.arguments
        ) { backStackEntry ->

            val tripId = backStackEntry.arguments?.getInt("tripId")
            val placeId = backStackEntry.arguments?.getInt("placeId")

            requireNotNull(tripId) { "El ID del viaje es nulo" }
            requireNotNull(placeId) { "El ID del lugar es nulo" }

            PlaceDetailScreen(
                navController = navController,
                tripId = tripId,
                placeId = placeId
            )
        }

        composable(
            route = NavRoutes.PlaceForm.route,
            arguments = NavRoutes.PlaceForm.arguments
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getInt("tripId")
            val placeId = backStackEntry.arguments?.getInt("placeId")

            requireNotNull(tripId) { "El ID del viaje es nulo" }
            requireNotNull(placeId) { "El ID del lugar es nulo" }

             PlaceFormScreen(
                 navController = navController,
                 tripId = tripId,
                 placeId = if (placeId == -1) null else placeId
             )
        }
    }
}