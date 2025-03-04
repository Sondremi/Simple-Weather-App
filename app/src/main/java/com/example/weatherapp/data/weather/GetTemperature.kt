package com.example.weatherapp.data.weather

import com.example.weatherapp.data.location.FetchCoordinates

suspend fun getTemperature(city: String): String {
    val fetchCoordinates = FetchCoordinates()
    val fetchWeatherData = FetchWeatherData()

    val coordinates = fetchCoordinates.getCoordinates(city)
    return if (coordinates != null) {
        fetchWeatherData.getTemperatureData(coordinates.lat, coordinates.lon)
    } else {
        "Kunne ikke hente koordinater for byen"
    }
}
