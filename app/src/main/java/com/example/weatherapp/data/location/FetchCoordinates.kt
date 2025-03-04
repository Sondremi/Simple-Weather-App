package com.example.weatherapp.data.location

import com.example.weatherapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getCoordinates(city: String): Coordinates? {
    val urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=$city&key=${BuildConfig.MAPS_API_KEY}"

    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            val responseText = connection.inputStream.bufferedReader().use { it.readText() }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val json = JSONObject(responseText)
                val results = json.getJSONArray("results")
                if (results.length() > 0) {
                    val location = results.getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location")
                    val lat = location.getDouble("lat")
                    val lon = location.getDouble("lng")
                    return@withContext Coordinates(lat, lon)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }
}
