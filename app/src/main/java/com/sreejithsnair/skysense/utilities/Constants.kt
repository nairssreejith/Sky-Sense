package com.sreejithsnair.skysense.utilities

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sreejithsnair.skysense.R

object Constants {
    val API_KEY = "fd369fb4b7e64f15ba7225102240606"

    val customFontFamily = FontFamily(
        Font(R.font.samsungsans_bold, FontWeight.Bold),
        Font(R.font.samsungsans_thin, FontWeight.Thin),
        Font(R.font.samsungsans_medium, FontWeight.Medium),
        Font(R.font.samsungsans_regular, FontWeight.Normal)
    )

    val weatherAppCardBackground = R.drawable.card_background

    val iconWind = R.drawable.wind_24dp
    val iconHumidity = R.drawable.humidity_24dp
    val iconDew = R.drawable.dew_24dp
    val iconPressure = R.drawable.pressure_24dp
    val iconVisibility = R.drawable.visibility_24dp
    val iconUv = R.drawable.uv_24dp

    val titleWind = "Wind"
    val titleHumidity = "Humidity"
    val titleDew = "Dew point"
    val titlePressure = "Pressure"
    val titleVisibility = "Visibility"
    val titleUv = "UV index"
}