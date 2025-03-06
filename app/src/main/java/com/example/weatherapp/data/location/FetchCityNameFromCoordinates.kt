package com.example.weatherapp.data.location

import com.example.weatherapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getCityNameFromCoordinates(coordinates: Coordinates): String {
    val url = "https://api.mapbox.com/geocoding/v5/mapbox.places/${coordinates.lon},${coordinates.lat}.json?access_token=${BuildConfig.MAP_BOX_API_KEY}"

    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val features = json.getJSONArray("features")

                if (features.length() > 0) {
                    return@withContext features.getJSONObject(0).getString("place_name")
                }
            }
            "Ukjent sted"
        } catch (e: Exception) {
            "Feil: ${e.message}"
        }
    }
}
