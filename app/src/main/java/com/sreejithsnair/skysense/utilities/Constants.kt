package com.sreejithsnair.skysense.utilities

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sreejithsnair.skysense.R

object Constants {
    const val API_KEY = "fd369fb4b7e64f15ba7225102240606"

    val customFontFamily = FontFamily(
        Font(R.font.samsungsans_bold, FontWeight.Bold),
        Font(R.font.samsungsans_thin, FontWeight.Thin),
        Font(R.font.samsungsans_medium, FontWeight.Medium),
        Font(R.font.samsungsans_regular, FontWeight.Normal)
    )

    val cardTranslucentBackground = R.drawable.card_background

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

    val appHubDescription = "Welcome to Android App Hub – your one-stop destination to explore the diverse capabilities of Android through an array of mini-apps, each designed to demonstrate a specific feature or functionality. \n\nKey Features:\n" +
            "1. Intuitive User Interface:\n" +
            "Navigate effortlessly through a clean, user-friendly interface that organizes mini-apps by category and functionality.\n" +
            "\n" +
            "2. Mini-Apps Collection:\n" +
            "Explore a variety of mini-apps, each showcasing a different aspect of Android:"

    val taskMasterDescription = "ToDo App is a sleek, modern ToDo application built using Jetpack Compose, Android's modern toolkit for building native UI. The app leverages Room Database for efficient local data storage, ensuring your tasks are persistently saved and easily retrievable."
}