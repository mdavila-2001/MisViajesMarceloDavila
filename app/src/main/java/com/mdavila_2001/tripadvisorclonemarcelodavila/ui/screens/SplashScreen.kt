package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.window.SplashScreenView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.R
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController = rememberNavController(),
    viewModel: SplashViewModel = viewModel()
) {
    val destination by viewModel.destinationRoute.collectAsState()

    LaunchedEffect(destination) {
        if (destination != null){
            navController.navigate(destination!!) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SplashView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Asumiendo que guardaste el logo en 'res/drawable/logo_viajes_app.png'
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Logo de la App",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TripAdvisorCloneMarceloDavilaTheme {
        SplashView()
    }
}