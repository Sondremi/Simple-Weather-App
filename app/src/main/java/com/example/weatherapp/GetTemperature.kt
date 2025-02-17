package com.example.weatherapp

suspend fun getTemperature(city: String): String {
    val fetchCoordinates = FetchCoordinates()
    val fetchWeatherData = FetchWeatherData()

    val coordinates = fetchCoordinates.getCoordinates(city)
    return if (coordinates != null) {
        fetchWeatherData.getTemperatureData(coordinates.lat, coordinates.lon)
    } else {
        "Kunne ikke hente koordinater for $city"
    }
}
