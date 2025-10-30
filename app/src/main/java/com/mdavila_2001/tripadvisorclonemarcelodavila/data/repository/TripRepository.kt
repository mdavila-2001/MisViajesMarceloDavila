package com.mdavila_2001.tripadvisorclonemarcelodavila.data.repository

import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.ApiService
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance

class TripRepository (
    private val apiService: ApiService = RetroFitInstance.apiService,
) {}