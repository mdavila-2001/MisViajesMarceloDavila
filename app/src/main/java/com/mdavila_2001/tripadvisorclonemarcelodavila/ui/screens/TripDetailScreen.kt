package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.NavRoutes
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.places.PlaceList
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

    val context = LocalContext.current

    val placeToDelete = remember { mutableStateOf<Place?>(null) }

    LaunchedEffect(key1 = viewModel.deleteSuccess) {
        viewModel.deleteSuccess.collect { success ->
            if (success) {
                Toast.makeText(context, "Lugar eliminado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
        floatingActionButton = {
            if (uiState.isMyTrip) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(NavRoutes.PlaceForm.route)
                    }
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        "Agregar Lugar")
                }
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
                    onPlaceClick = { place ->
                        navController.navigate(
                            NavRoutes.PlaceDetail.createRoute(
                                tripId = place.tripId,
                                place.id
                            )
                        )
                    },
                    isMyTrip = uiState.isMyTrip,
                    onEditClick = {
                        navController.navigate(
                            NavRoutes.PlaceForm.createRoute(tripId)
                        )
                    },
                    onDeleteClick = { place ->
                        placeToDelete.value = place
                    }
                )
            }

            // Diálogo de confirmación para eliminar lugar
            val placeForDialog = placeToDelete.value
            if (placeForDialog != null) {
                AlertDialog(
                    onDismissRequest = { placeToDelete.value = null },
                    title = { Text(text = "Confirmar eliminación") },
                    text = { Text(text = "¿Estás seguro que deseas eliminar '${placeForDialog.name}'?") },
                    confirmButton = {
                        TextButton(onClick = {
                            // Llamar al ViewModel para eliminar y cerrar el diálogo
                            viewModel.onDeletePlace(placeForDialog)
                            placeToDelete.value = null
                        }) {
                            Text("Sí")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { placeToDelete.value = null }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}