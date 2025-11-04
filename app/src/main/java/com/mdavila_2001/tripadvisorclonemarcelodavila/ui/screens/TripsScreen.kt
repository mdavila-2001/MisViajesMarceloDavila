package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.LoginViewModel
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.TripsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    navController: NavController,
    viewModel: TripsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
}