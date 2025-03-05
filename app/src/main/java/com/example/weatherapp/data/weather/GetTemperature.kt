package com.example.weatherapp.data.weather

import com.example.weatherapp.data.location.getCoordinates

suspend fun getTemperature(city: String): String {
    val coordinates = getCoordinates(city)
    return if (coordinates != null) {
        getTemperatureData(coordinates.lat, coordinates.lon)
    } else {
        "Kunne ikke hente koordinater"
    }
}
