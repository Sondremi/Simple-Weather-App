package com.example.weatherapp.ui.weatherApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.location.Coordinates
import com.example.weatherapp.data.location.getCityNameFromCoordinates
import com.example.weatherapp.data.map.MapViewer
import com.example.weatherapp.data.weather.getTemperature
import com.example.weatherapp.data.weather.getTemperatureFromCity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WeatherApp() {
    var temperature by remember { mutableStateOf("") }
    var cityInput by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var displayedCity by remember { mutableStateOf("") }
    var selectedCoordinates by remember { mutableStateOf<Coordinates?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Weather App",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color(0xFF1C1C1E),
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MapViewer(
                city = selectedCity,
                onCoordinatesSelected = { newCoordinates ->
                    selectedCoordinates = newCoordinates
                    CoroutineScope(Dispatchers.IO).launch {
                        val fullAddress = getCityNameFromCoordinates(newCoordinates)
                        selectedCity = fullAddress
                        displayedCity = fullAddress.split(",").firstOrNull() ?: fullAddress
                        temperature = getTemperature(selectedCoordinates!!.lat, selectedCoordinates!!.lon)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cityInput,
                onValueChange = { cityInput = it },
                label = { Text("Skriv inn by") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        selectedCity = cityInput
                        displayedCity = cityInput
                        CoroutineScope(Dispatchers.IO).launch {
                            temperature = getTemperatureFromCity(cityInput)
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedCity = cityInput
                    displayedCity = cityInput
                    CoroutineScope(Dispatchers.IO).launch {
                        temperature = getTemperatureFromCity(cityInput)
                    }
                    keyboardController?.hide()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text(text = "Hent Temperatur", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if ("°C" in temperature) {
                    "Temperaturen i $displayedCity er: $temperature"
                } else {
                    temperature
                },
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF1C1C1E)
            )
        }
    }
}
