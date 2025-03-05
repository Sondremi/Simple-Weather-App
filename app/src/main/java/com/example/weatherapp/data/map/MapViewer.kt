package com.example.weatherapp.data.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.location.Coordinates
import com.example.weatherapp.data.location.getCoordinates
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.geojson.Point.fromLngLat
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardSatelliteStyle

@Composable
fun MapViewer(city: String) {
    val defaultCoordinates = Coordinates(59.9138688, 10.7522454) // Oslo
    var coordinates by remember { mutableStateOf(defaultCoordinates) }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(fromLngLat(defaultCoordinates.lon, defaultCoordinates.lat))
            pitch(0.0)
            bearing(0.0)
        }
    }

    LaunchedEffect(city) {
        if (city.isNotEmpty()) {
            val newCoordinates = getCoordinates(city)
            coordinates = newCoordinates ?: defaultCoordinates
        }
    }

    LaunchedEffect(coordinates) {
        mapViewportState.setCameraOptions {
            center(fromLngLat(coordinates.lon, coordinates.lat))
            zoom(12.0)
        }
    }

    MapboxMap(
        Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(12.dp)),
        mapViewportState = mapViewportState,
        style = {
            MapboxStandardSatelliteStyle{
                    lightPreset = LightPresetValue.DEFAULT
                    showPlaceLabels = BooleanValue(true)
            }
        }
    )
}
