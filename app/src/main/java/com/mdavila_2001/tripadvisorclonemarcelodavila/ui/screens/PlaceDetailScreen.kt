package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.places.PlaceDetailContent
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.PlaceDetailViewModel

class PlaceDetailViewModelFactory(
    private val application: Application,
    private val tripId: Int,
    private val placeId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceDetailViewModel(application, tripId, placeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    navController: NavController,
    tripId: Int,
    placeId: Int
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: PlaceDetailViewModel = viewModel(
        factory = PlaceDetailViewModelFactory(application, tripId, placeId)
    )

    val uiState by viewModel.uiState.collectAsState()
    val place = uiState.place

    Scaffold(
        topBar = {
            AppBar(
                title = place?.name ?: "Detalle del Lugar",
                backEnabled = true,
                logOutEnabled = false,
                onLogoutClick = {},
                onBackClick = { navController.popBackStack() },
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (place != null) {
                PlaceDetailContent(place = place)
            } else {
                Text(
                    text = "No se pudo cargar el lugar.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}