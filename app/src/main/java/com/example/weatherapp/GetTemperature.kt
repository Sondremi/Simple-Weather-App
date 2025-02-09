package com.example.weatherapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun getTemperature(city: String): String {
    val coordinates = getCoordinatesFromCity(city) ?: return "Feil: fant ikke byen"
    return fetchWeatherData(coordinates.lat, coordinates.lon)
}

