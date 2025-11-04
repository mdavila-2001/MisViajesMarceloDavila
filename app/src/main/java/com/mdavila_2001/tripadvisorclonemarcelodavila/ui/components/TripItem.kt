package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip

@Composable
fun TripItem(
    trip: Trip,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = trip.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text("País: ${trip.country}")
            Text(
                "Creado por: ${trip.username}",
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripItemPreview() {
    val sampleTrip = Trip(
        id = "1",
        name = "Aventura en los Andes",
        country = "Perú",
        username = "marcelodavila",
        createdAt = "2023-01-01",
        updatedAt = "2023-01-02"
    )
    TripItem(trip = sampleTrip, onClick = {})
}