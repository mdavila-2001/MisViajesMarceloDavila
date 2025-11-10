package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.mdavila_2001.tripadvisorclonemarcelodavila.R
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.PlaceFormViewModel

class PlaceFormViewModelFactory(
    private val application: Application,
    private val tripId: Int,
    private val placeId: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceFormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceFormViewModel(application, tripId, placeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceFormScreen(
    navController: NavController,
    tripId: Int,
    placeId: Int?,
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: PlaceFormViewModel = viewModel(
        factory = PlaceFormViewModelFactory(application, tripId, placeId)
    )

    val uiState by viewModel.uiState.collectAsState()
    val navigateBack by viewModel.navigateBack.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.onImageSelected(uri)
            }
        }
    )

    LaunchedEffect(navigateBack) {
        if (navigateBack) {
            navController.previousBackStackEntry?.savedStateHandle?.set("places_updated", true)
            navController.popBackStack()
            viewModel.onNavigationDone()
        }
    }
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            viewModel.onToastShown()
        }
    }
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.onErrorMessageShown()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = if (placeId == null) "Crear Lugar" else "Editar Lugar",
                logOutEnabled = false,
                backEnabled = true,
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutClick = { },
                modifier = Modifier,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    if (uiState.imageUrl.isNotBlank()) uiState.imageUrl
                                    else uiState.selectedImageUri
                                )
                                .placeholder(R.drawable.splash)
                                .error(R.drawable.splash)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Imagen del lugar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                imagePickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            enabled = !uiState.isUploadingImage
                        ) {
                            Text("Seleccionar Imagen")
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.onNameChange(it) },
                        label = { Text("Nombre del Lugar (Obligatorio)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {

                    OutlinedTextField(
                        value = uiState.city,
                        onValueChange = { viewModel.onCityChange(it) },
                        label = { Text("Ciudad (Obligatorio)") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onDescriptionChange(it) },
                        label = { Text("Descripción") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.imageUrl,
                        onValueChange = { viewModel.onImageUrlChange(it) },
                        label = { Text("URL de la Imagen (generada por servicio externo)") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.timeToSpend,
                        onValueChange = { viewModel.onTimeChange(it) },
                        label = { Text("Tiempo a Pasar") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.price,
                        onValueChange = { viewModel.onPriceChange(it) },
                        label = { Text("Precio (ej: 20$ por persona)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.directions,
                        onValueChange = { viewModel.onDirectionsChange(it) },
                        label = { Text("Cómo Llegar") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                    )
                }
                item {
                    Button(
                        onClick = { viewModel.onSaveClick() },
                        enabled = !uiState.isLoading && !uiState.isUploadingImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(if (placeId == null) "Guardar Lugar" else "Actualizar Lugar")
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceFormScreenPreview() {
    TripAdvisorCloneMarceloDavilaTheme {
        PlaceFormScreen(
            navController = rememberNavController(),
            tripId = 1,
            placeId = null
        )
    }
}