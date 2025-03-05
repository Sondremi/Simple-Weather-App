package com.example.weatherapp.data.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.location.Coordinates
import com.example.weatherapp.data.location.getCoordinates
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.geojson.Point.fromLngLat
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardSatelliteStyle
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.Style


@Composable
fun MapViewer(city: String) {
    val defaultCoordinates = Coordinates(59.9138688, 10.7522454) // Oslo
    var coordinates by remember { mutableStateOf(defaultCoordinates) }
    var isDarkMode by remember { mutableStateOf(true) }

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

    Box {
        MapboxMap(
            Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(12.dp)),
            mapViewportState = mapViewportState,
            style = {
                MapboxStandardStyle {
                    if (isDarkMode) {
                        lightPreset = LightPresetValue.NIGHT
                    } else {
                        lightPreset = LightPresetValue.DAY
                    }
                    showPlaceLabels = BooleanValue(true)
                }
            }
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = { isDarkMode = true },
                modifier = Modifier
                    .size(80.dp, 32.dp)
                    .padding(end = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Dark Mode", fontSize = 12.sp)
            }
            Button(
                onClick = { isDarkMode = false },
                modifier = Modifier
                    .size(80.dp, 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Light", fontSize = 12.sp)
            }
        }
    }
}
