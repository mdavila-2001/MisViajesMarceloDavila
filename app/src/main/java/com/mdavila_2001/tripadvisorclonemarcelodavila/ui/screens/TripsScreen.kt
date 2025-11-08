package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.NavRoutes
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.BottomNavigationBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.trips.TripList
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.Tab
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.TripsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    navController: NavController,
    viewModel: TripsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val navigateToLogin by viewModel.navigateToLogin.collectAsState()

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin){
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadData()
    }

    val showLogoutDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                title = if (uiState.selectedTab == Tab.TRIPS) "Viajes" else "Mis Viajes",
                backEnabled = false,
                onLogoutClick = {
                    showLogoutDialog.value = true
                },
                onBackClick = {},
                modifier = Modifier,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRoutes.TripForm.createRoute())
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    "Crear Viaje"
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = uiState.selectedTab.ordinal,
                onTabSelected = { index ->
                    viewModel.selectTab(Tab.entries[index])
                },
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if(uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                TripList(
                    trips = uiState.currentList,
                    onTripClick = { trip ->
                        navController.navigate(
                            NavRoutes.TripDetail.createRoute(
                                tripId = trip.id,
                                tripName = trip.name,
                                tripOwner = trip.username
                            )
                        )
                    },
                    isMyTrip = (uiState.selectedTab == Tab.MY_TRIPS),
                    onEditClick = { trip ->
                        navController.navigate(
                            NavRoutes.TripForm.createEditRoute(
                                tripId = trip.id,
                                name = trip.name,
                                country = trip.country
                            )
                        )
                    },

                    onDeleteClick = { trip ->
                        viewModel.onDeleteTrip(trip)
                    }
                )
            }

            if (showLogoutDialog.value) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog.value = false },
                    title = { Text(text = "Confirmar cierre de sesión") },
                    text = { Text(text = "¿Estás seguro que quieres cerrar sesión?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog.value = false
                            viewModel.onLogoutClicked()
                        }) {
                            Text("Sí")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog.value = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripsScreenPreview() {
    TripAdvisorCloneMarceloDavilaTheme() {
        TripsScreen(
            navController = NavController(LocalContext.current)
        )
    }
}