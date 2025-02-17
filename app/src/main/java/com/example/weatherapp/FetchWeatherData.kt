package com.example.weatherapp

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class FetchWeatherData {
    fun getTemperatureData(lat: Double, lon: Double): String {
        val urlString =
            "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=$lat&lon=$lon"

        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "WeatherApp/1.0 (contact@example.com)")
            connection.connect()

            val responseCode = connection.responseCode
            val responseText = connection.inputStream.bufferedReader().use { it.readText() }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val json = JSONObject(responseText)
                val temp = json.getJSONObject("properties")
                    .getJSONArray("timeseries").getJSONObject(0)
                    .getJSONObject("data").getJSONObject("instant")
                    .getJSONObject("details").getDouble("air_temperature")
                "$temp°C"
            } else {
                "Feil: ${connection.responseCode}"
            }
        } catch (e: Exception) {
            "Feil ved henting av værdata: ${e.message}"
        }
    }
}