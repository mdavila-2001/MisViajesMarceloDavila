package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.TripList
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.LoginViewModel
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.Tab
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.TripsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    navController: NavController,
    viewModel: TripsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadData()
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "Viajes",
                modifier = Modifier,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.Add,
                    "Crear Viaje"
                )
            }
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
                    onTripClick = {
                        //navController.navigate("trip/${it.id}")
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