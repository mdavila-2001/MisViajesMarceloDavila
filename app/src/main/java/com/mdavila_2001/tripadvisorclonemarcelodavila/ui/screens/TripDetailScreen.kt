package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.PlaceList
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.TripDetailViewModel

class TripDetailViewModelFactory(
    private val application: Application,
    private val tripId: Int,
    private val tripOwner: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripDetailViewModel(application, tripId, tripOwner) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailScreen(
    navController: NavController,
    tripId: Int,
    tripName: String,
    tripOwner: String
) {
    val application = LocalContext.current.applicationContext as Application

    val viewModel: TripDetailViewModel = viewModel(
        factory = TripDetailViewModelFactory(application, tripId, tripOwner)
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = tripName,
                backEnabled = true,
                onLogoutClick = { },
                onBackClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        },
        bottomBar = {
            if (uiState.isMyTrip) {
                BottomAppBar(
                    actions = {},
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {}
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                "Agregar Lugar")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.errorMessage != null) {
                Text(
                    uiState.errorMessage!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                PlaceList(
                    places = uiState.places,
                    modifier = Modifier,
                    onPlaceClick = { }
                )
            }
        }
    }
}