package com.example.weatherapp.data.map

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MapViewer() {
    Box(
        modifier = Modifier
            .height(380.dp)
            .fillMaxWidth()
            .border(2.dp, Color.Black),
    ) {
        Text(text = "Map Viewer")
    }

}