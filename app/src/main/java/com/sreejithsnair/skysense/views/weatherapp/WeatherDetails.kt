package com.sreejithsnair.skysense.views.weatherapp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.utilities.Constants.iconDew
import com.sreejithsnair.skysense.utilities.Constants.iconHumidity
import com.sreejithsnair.skysense.utilities.Constants.iconPressure
import com.sreejithsnair.skysense.utilities.Constants.iconUv
import com.sreejithsnair.skysense.utilities.Constants.iconVisibility
import com.sreejithsnair.skysense.utilities.Constants.iconWind
import com.sreejithsnair.skysense.utilities.Constants.titleDew
import com.sreejithsnair.skysense.utilities.Constants.titleHumidity
import com.sreejithsnair.skysense.utilities.Constants.titlePressure
import com.sreejithsnair.skysense.utilities.Constants.titleUv
import com.sreejithsnair.skysense.utilities.Constants.titleVisibility
import com.sreejithsnair.skysense.utilities.Constants.titleWind
import com.sreejithsnair.skysense.utilities.Constants.weatherAppCardBackground
import com.sreejithsnair.skysense.model.WeatherModel

@Composable
fun WeatherDetails(data: WeatherModel){
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherPrimaryDetails(data)

    }
}

@Composable
fun WeatherPrimaryDetails(data: WeatherModel){

    val animation = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        animation.animateTo(
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ){
            Icon(
                imageVector = Icons.Default.LocationOn,
                tint = Color.White,
                contentDescription = "Location icon",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${data.location.name},",
                fontSize = 24.sp,
                fontFamily = Constants.customFontFamily,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = data.location.country,
                fontSize = 18.sp,
                fontFamily = Constants.customFontFamily,
                color = Color(0xFFDDDDDD)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val temperature = data.current.temp_c.split(".")
            Text(
                text = "${temperature.firstOrNull() ?: ""}°",
                fontSize = 80.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.White,
                fontFamily = Constants.customFontFamily,
                modifier = Modifier.weight(1f)
            )

            AsyncImage(
                modifier = Modifier
                    .size(128.dp)
                    .graphicsLayer(
                        scaleX = animation.value,
                        scaleY = animation.value
                    ),
                model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                contentDescription = "Condition Icon",
                alignment = Alignment.CenterEnd
            )
        }

        Text(
            text = data.current.condition.text,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFDDDDDD)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }

    Column (
        modifier = Modifier
            .fillMaxWidth(),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherComponentDetailLeftCard(iconUv, titleUv, data.current.uv)
            Spacer(modifier = Modifier.width(8.dp))
            WeatherComponentDetailRightCard(iconHumidity, titleHumidity, "${data.current.humidity}%")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherComponentDetailLeftCard(iconWind, titleWind, "${data.current.wind_kph} km/h")
            Spacer(modifier = Modifier.width(8.dp))
            WeatherComponentDetailRightCard(iconDew, titleDew, "${data.current.dewpoint_c}°")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherComponentDetailLeftCard(iconPressure, titlePressure, "${data.current.pressure_mb} mb")
            Spacer(modifier = Modifier.width(8.dp))
            WeatherComponentDetailRightCard(iconVisibility, titleVisibility, "${data.current.vis_km} km")
        }
    }
}

@Composable
fun WeatherComponentDetailLeftCard(drawableId: Int, title: String, value: String?){

    Card (
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(0.5f)
    ){
        WeatherDetailsCardContent(drawableId, title, value)
    }
}
@Composable
fun WeatherComponentDetailRightCard(drawableId: Int, title: String, value: String?){

    Card (
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ){
        WeatherDetailsCardContent(drawableId, title, value)
    }
}

@Composable
fun WeatherDetailsCardContent(drawableId: Int, title: String, value: String?){

    val backgroundPainter: Painter = painterResource(weatherAppCardBackground)

    Box{
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Column (
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(painter = painterResource(id = drawableId), contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = Constants.customFontFamily,
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value?:"-",
                fontSize = 20.sp,
                fontFamily = Constants.customFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

    }
}

