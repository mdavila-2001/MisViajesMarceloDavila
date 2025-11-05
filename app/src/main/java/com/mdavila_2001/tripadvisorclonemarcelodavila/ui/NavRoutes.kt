package com.mdavila_2001.tripadvisorclonemarcelodavila.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavRoutes(val route: String){
    object Splash: NavRoutes("splash")

    object Login: NavRoutes("login")

    object Trips: NavRoutes("trips")

    object TripDetails: NavRoutes("trip_details/{tripId}{tripName}/{tripOwner}"){
        val arguments = listOf(
            navArgument("tripId") { type = NavType.IntType },
            navArgument("tripName") { type = NavType.StringType },
            navArgument("tripOwner") { type = NavType.StringType }
        )

        fun createRoute(tripId: Int, tripName: String, tripOwner: String): String {
            val encodedName = tripName.replace(" ", "_")
            return "trip_detail/$tripId/$encodedName/$tripOwner"
        }
    }
}