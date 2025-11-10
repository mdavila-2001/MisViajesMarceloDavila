package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.mdavila_2001.tripadvisorclonemarcelodavila.R
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.global.AppBar
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme

@Composable
fun PlaceDetailContent(place: Place) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.imageUrl?.replace("\\/", "/"))
                    .placeholder(R.drawable.splash)
                    .build(),
                contentDescription = place.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = place.city,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!place.description.isNullOrBlank()) {
                    Text(
                        text = place.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))
            }
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if(!place.timeToSpend.isNullOrBlank()) {
                    PlaceInfoRow(
                        icon = Icons.Default.DateRange,
                        title = "Tiempo Recomendado",
                        text = place.timeToSpend,
                    )
                }

                if(!place.price.isNullOrBlank()) {
                    PlaceInfoRow(
                        icon = Icons.Default.ShoppingCart,
                        title = "Precio",
                        text = place.price,
                    )
                }

                if(!place.directions.isNullOrBlank()) {
                    PlaceInfoRow(
                        icon = Icons.Default.LocationOn,
                        title = "Indicaciones",
                        text = place.directions,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailContentPreview() {
    TripAdvisorCloneMarceloDavilaTheme {
        val samplePlace = Place(
            id = 1,
            name = "Machu Picchu",
            city = "Cusco",
            description = "Antigua ciudadela inca ubicada en las montañas de los Andes en Perú.",
            imageUrl = "https://example.com/image.png",
            directions = "Tomar un tren de Cusco a Aguas Calientes, luego caminar.",
            timeToSpend = "4-6 horas",
            price = "$50 por persona",
            tripId = 1,
            createdAt = "",
            updatedAt = ""
        )

        Scaffold(
            topBar = {
                AppBar(
                    title = samplePlace.name,
                    logOutEnabled = false,
                    backEnabled = true,
                    onBackClick = {},
                    onLogoutClick = {},
                    modifier = Modifier
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                PlaceDetailContent(place = samplePlace)
            }
        }
    }
}