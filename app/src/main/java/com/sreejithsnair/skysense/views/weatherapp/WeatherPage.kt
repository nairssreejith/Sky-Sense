package com.sreejithsnair.skysense.views.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreejithsnair.skysense.R
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.repository.NetworkResponse
import com.sreejithsnair.skysense.viewmodel.WeatherViewModel
import com.sreejithsnair.skysense.views.common.GenericCardContent
import java.io.File

@Composable
fun WeatherPage(viewModel: WeatherViewModel){

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()
    val scrollState = rememberScrollState()

    val keyBoardController = LocalSoftwareKeyboardController.current
    val backgroundImage = painterResource(id = R.drawable.weather_app_bg)
    val weatherAppTitle = stringResource(id = R.string.weather_app_title)
    val weatherAppDescription = stringResource(id = R.string.weather_app_content_description)


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        GenericCardContent(weatherAppTitle, weatherAppDescription)

        Column (
            modifier = Modifier
                .wrapContentHeight()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = city,
                    onValueChange = {
                        city = it
                    },
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color(0xFFDDDDDD)
                    ),
                    textStyle = TextStyle(
                        fontFamily = Constants.customFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    ),
                    label = {
                        Text(
                            text = "Search for any location",
                            fontFamily = Constants.customFontFamily,
                            color = Color(0xFFDDDDDD)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.getWeatherData(city)
                            city = ""
                            keyBoardController?.hide()
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when(val result = weatherResult.value){
                is NetworkResponse.Success -> {
                    backgroundSetter(result.data.current.condition.text)
                    WeatherDetails(data = result.data)
                }
                is NetworkResponse.Error -> {
                    Text(text = result.errorMessage,
                        style = TextStyle(
                            color = Color.Red,
                            fontFamily = Constants.customFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    )
                }
                is NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }
                null -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }



}

fun backgroundSetter(weather: String){

}


