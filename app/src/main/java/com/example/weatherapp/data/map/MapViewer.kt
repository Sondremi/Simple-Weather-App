package com.example.weatherapp.data.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.data.location.Coordinates
import com.example.weatherapp.data.location.getCoordinates
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.geojson.Point.fromLngLat
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage

@Composable
fun MapViewer(city: String, onCoordinatesSelected: (Coordinates) -> Unit) {
    val defaultCoordinates = Coordinates(59.9138688, 10.7522454) // Oslo
    var coordinates by remember { mutableStateOf(defaultCoordinates) }
    var isDarkMode by remember { mutableStateOf(false) }
    var selectedStyle by remember { mutableStateOf(Style.STANDARD) }
    var zoom by remember { mutableDoubleStateOf(12.0) }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(zoom)
            center(fromLngLat(defaultCoordinates.lon, defaultCoordinates.lat))
            pitch(0.0)
            bearing(0.0)
        }
    }

    LaunchedEffect(city) {
        if (city.isNotEmpty()) {
            val newCoordinates = getCoordinates(city)
            if (newCoordinates != null && newCoordinates != coordinates) {
                coordinates = newCoordinates
            }
        }
    }

    LaunchedEffect(coordinates) {
        zoom = mapViewportState.cameraState?.zoom ?: 12.0
        mapViewportState.setCameraOptions {
            center(fromLngLat(coordinates.lon, coordinates.lat))
            zoom(zoom)
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
                if (selectedStyle == Style.STANDARD) {
                    MapboxStandardStyle {
                        lightPreset = if (isDarkMode) {
                            LightPresetValue.NIGHT
                        } else {
                            LightPresetValue.DAY
                        }
                    }
                } else {
                    MapStyle(style = selectedStyle)
                }
            },
            onMapClickListener = { point ->
                val newCoordinates = Coordinates(point.latitude(), point.longitude())
                coordinates = newCoordinates
                onCoordinatesSelected(newCoordinates)
                true
            },
            scaleBar = { },
            logo = { },
            attribution = { }
        ) {
            val marker = rememberIconImage(R.drawable.red_marker)
            PointAnnotation(point = fromLngLat(coordinates.lon, coordinates.lat)) {
                iconImage = marker
            }
        }

        Column(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopStart)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.7f))
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    MapButton("Standard") { selectedStyle = Style.STANDARD }
                    Spacer(modifier = Modifier.width(4.dp))
                    MapButton("Satellite") { selectedStyle = Style.STANDARD_SATELLITE }
                    Spacer(modifier = Modifier.width(4.dp))
                    MapButton("Outdoors") { selectedStyle = Style.OUTDOORS }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (selectedStyle == Style.STANDARD) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        MapButton("Light") { isDarkMode = false }
                        Spacer(modifier = Modifier.width(4.dp))
                        MapButton("Dark") { isDarkMode = true }
                    }
                }
            }
        }
    }
}
