package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.TripFormViewModel

class TripFormViewModelFactory(
    private val application: Application,
    private val tripId: Int?,
    private val name: String?,
    private val country: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripFormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripFormViewModel(application, tripId, name, country) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripFormScreen(
    navController: NavController,
    tripId: Int?,
    name: String?,
    country: String?,
) {
    val application = LocalContext.current.applicationContext as Application

    val viewModel: TripFormViewModel = viewModel(
        factory = TripFormViewModelFactory(application, tripId, name, country)
    )

    val uiState by viewModel.uiState.collectAsState()
    val navigateBack by viewModel.navigateBack.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(navigateBack) {
        if (navigateBack) {
            navController.popBackStack()
            viewModel.onNavigationDone()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = if (tripId == null) "Crear Viaje" else "Editar Viaje",
                logOutEnabled = false,
                backEnabled = true,
                onLogoutClick = { },
                onBackClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.onNameChange(it) },
                        label = { Text("Nombre del Viaje") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = uiState.country,
                        onValueChange = { viewModel.onCountryChange(it) },
                        label = { Text("Nombre del País") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.onSaveClick() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (tripId == null) "Crear Viaje" else "Guardar Cambios")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripFormScreenPreview() {
    TripAdvisorCloneMarceloDavilaTheme {
        TripFormScreen(
            navController = rememberNavController(),
            tripId = 1,
            name = "Ida a Disney",
            country = "Estados Unidos"
        )
    }
}