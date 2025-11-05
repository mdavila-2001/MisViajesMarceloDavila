package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip

@Composable
fun TripList(
    trips: List<Trip>,
    onTripClick: (Trip) -> Unit
) {
    if(trips.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay viajes disponibles por el momento.")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(trips.size) { trip ->
                TripItem(
                    trip = trips[trip],
                    onClick = {
                        onTripClick(trips[trip])
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripListPreview() {
    val sampleTrips = listOf(
        Trip(
            id = 1,
            name = "Aventura en los Andes",
            country = "Perú",
            username = "marcelo.davila",
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        ),
        Trip(
            id = 2,
            name = "Explorando la Patagonia",
            country = "Argentina",
            username = "juan.perez",
            createdAt = "2023-02-01",
            updatedAt = "2023-02-02"
        )
    )
    TripList(
        trips = sampleTrips,
        onTripClick = {}
    )
}