package com.mdavila_2001.tripadvisorclonemarcelodavila.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavRoutes(val route: String){
    object Splash: NavRoutes("splash")

    object Login: NavRoutes("login")

    object Trips: NavRoutes("trips")

    object TripForm: NavRoutes(
        "trip_form" +
                "?tripId={tripId}&name={name}&country={country}"
    ) {
        val arguments = listOf(
            navArgument("tripId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("name") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("country") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )

        fun createRoute() = "trip_form"

        fun createEditRoute(tripId: Int, name: String, country: String): String {
            val encodedName = name.replace(" ", "_")
            val encodedCountry = country.replace(" ", "_")
            return "trip_form?tripId=$tripId&name=$encodedName&country=$encodedCountry"
        }
    }

    object TripDetail: NavRoutes("trip_detail/{tripId}/{tripName}/{tripOwner}"){

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