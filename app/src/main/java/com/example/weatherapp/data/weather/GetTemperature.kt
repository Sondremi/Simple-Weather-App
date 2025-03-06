package com.example.weatherapp.data.weather

import com.example.weatherapp.data.location.getCoordinates

suspend fun getTemperatureFromCity(city: String): String {
    val coordinates = getCoordinates(city)
    return if (coordinates != null) {
        getTemperature(coordinates.lat, coordinates.lon)
    } else {
        "Finner ikke stedet"
    }
}
