package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.mdavila_2001.tripadvisorclonemarcelodavila.R
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme

@Composable
fun PlaceItem(
    place: Place,
    onClick: () -> Unit,
    isMyTrip: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(place.imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .crossfade(true)
                        .build(),
                    contentDescription = place.name,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    Modifier.width(16.dp)
                )

                Column {
                    Text(text = place.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(text = place.city, style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (isMyTrip) {
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar Lugar"
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar Lugar",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceItemPreview() {
    TripAdvisorCloneMarceloDavilaTheme() {
        val samplePlace = Place(
            id = 1,
            name = "Machu Picchu",
            city = "Cusco",
            tripId = 1,
            description = "Ancient Incan city located in the Andes Mountains.",
            imageUrl = "data:image/jpeg;base64,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",
            directions = "Take a train from Cusco to Aguas Calientes, then hike up to the site.",
            timeToSpend = "4-6 hours",
            price = "$50",
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        )
        PlaceItem(
            place = samplePlace,
            onClick = {},
            isMyTrip = true,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}