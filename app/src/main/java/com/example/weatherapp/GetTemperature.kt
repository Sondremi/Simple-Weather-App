package com.example.weatherapp

suspend fun getTemperature(city: String): String {
    val coordinates = getCoordinatesFromCity(city) ?: return "Feil: fant ikke byen"
    return fetchWeatherData(coordinates.lat, coordinates.lon)
}

