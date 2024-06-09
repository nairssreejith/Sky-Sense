@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.sreejithsnair.skysense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.sreejithsnair.skysense.ui.theme.SkySenseTheme
import com.sreejithsnair.skysense.viewmodel.WeatherViewModel
import com.sreejithsnair.skysense.views.weatherapp.WeatherPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherViewModel = ViewModelProvider(owner = this)[WeatherViewModel::class.java]

        val myList = listOf("Weather App", "ToDo App Simple", "ToDo App Complex")

        setContent {
            SkySenseTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) { innerPadding ->
                    ListDetailLayout(listOfApps = myList, modifier = Modifier.padding(innerPadding), weatherViewModel = weatherViewModel)
                    //WeatherPage(weatherViewModel)
                }
            }
        }
    }
}

@Composable
fun ListDetailLayout(listOfApps: List<String>, modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel){

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            ListView(items = listOfApps, navigator)
        },
        detailPane = {
            DetailView(navigator = navigator, weatherViewModel = weatherViewModel)
        })
}

@Composable
fun ListView(items: List<String>, navigator: ThreePaneScaffoldNavigator<Any> ){
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(items){ item ->
            Text(
                item,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clickable {
                        navigator.navigateTo(
                            pane = ListDetailPaneScaffoldRole.Detail,
                            content = item
                        )
                    }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailView(navigator: ThreePaneScaffoldNavigator<Any>, weatherViewModel: WeatherViewModel){

    val content = navigator.currentDestination?.content?.toString() ?: "Select an app"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        when(content){
            "Weather App" -> WeatherPage(weatherViewModel)
            /*"ToDo App Simple" -> ToDoAppSimple()
            "ToDo App Complex" -> ToDoAppComplex()*/
        }
    }
}