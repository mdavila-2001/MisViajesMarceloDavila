package com.mdavila_2001.tripadvisorclonemarcelodavila.ui

sealed class NavRoutes(val route: String){
    object Splash: NavRoutes("splash")

    object Login: NavRoutes("login")

    object Trips: NavRoutes("trips")
}