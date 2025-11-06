package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place

@Composable
fun PlaceList(
    places: List<Place>,
    modifier: Modifier = Modifier, onPlaceClick: (Place) -> Unit
) {
    if(places.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aún no hay lugares disponibles.")
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(places) { place ->
                PlaceItem(
                    place = place,
                    onClick = {
                        onPlaceClick(place)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceListPreview() {
    val samplePlaces = listOf(
        Place(
            id = 1,
            name = "Machu Picchu",
            description = "Ancient Incan city in Peru",
            city = "Cusco",
            tripId = 1,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        ),
        Place(
            id = 2,
            name = "Eiffel Tower",
            description = "Iconic tower in Paris",
            city = "Paris",
            tripId = 2,
            createdAt = "2023-02-01",
            updatedAt = "2023-02-02"
        )
    )
    PlaceList(places = samplePlaces, onPlaceClick = {})
}