package com.example.weatherapp.data.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.data.location.Coordinates
import com.example.weatherapp.data.location.getCoordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapViewer(city: String) {
    val defaultCoordinates = Coordinates(59.9138688, 10.7522454) // Oslo
    var coordinates by remember { mutableStateOf(defaultCoordinates) }

    // Henter koordinatene kun når city endres og ikke er tomt
    LaunchedEffect(city) {
        if (city.isNotEmpty()) {
            val newCoordinates = getCoordinates(city)
            coordinates = newCoordinates ?: defaultCoordinates
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(coordinates.lat, coordinates.lon), 10f, 0f, 0f)
    }

    Box(
        modifier = Modifier
            .height(380.dp)
            .fillMaxWidth()
            .border(2.dp, Color.Black)
    ) {
//        GoogleMap(
//            cameraPositionState = cameraPositionState,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Marker(
//                title = "Valgt sted",
//                state = rememberMarkerState(
//                    position = LatLng(coordinates.lat, coordinates.lon)
//                )
//            )
//        }
        Text(text = city)
    }
}
