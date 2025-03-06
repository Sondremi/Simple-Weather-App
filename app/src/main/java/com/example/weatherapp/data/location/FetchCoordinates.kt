package com.example.weatherapp.data.location

import com.example.weatherapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getCoordinates(city: String): Coordinates? {
    val urlString = "https://api.mapbox.com/geocoding/v5/mapbox.places/$city.json?access_token=${BuildConfig.MAP_BOX_API_KEY}"

    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            val responseText = connection.inputStream.bufferedReader().use { it.readText() }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val json = JSONObject(responseText)
                val features = json.getJSONArray("features")
                if (features.length() > 0) {
                    val geometry = features.getJSONObject(0).getJSONObject("geometry")
                    val location = geometry.getJSONArray("coordinates")
                    val lon = location.getDouble(0)
                    val lat = location.getDouble(1)
                    return@withContext Coordinates(lat, lon)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }
}
