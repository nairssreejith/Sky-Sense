@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3AdaptiveApi::class)

package com.sreejithsnair.skysense

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.sreejithsnair.skysense.ui.theme.SkySenseTheme
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.utilities.CryptoManager
import com.sreejithsnair.skysense.viewmodel.WeatherViewModel
import com.sreejithsnair.skysense.views.weatherapp.WeatherPage
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherViewModel = ViewModelProvider(owner = this)[WeatherViewModel::class.java]

        val myList = listOf(getString(R.string.weather_app_title), "ToDo App Simple", "ToDo App Complex")
        val drawables = listOf(R.drawable.weather_app_icon_128, R.drawable.weather_app_bg, R.drawable.weather_app_bg)

        val cryptoManager = CryptoManager()

        setContent {
            SkySenseTheme {

                saveEncryptedDataInFile(context = applicationContext, cryptoManager = cryptoManager)
                
                Scaffold(modifier = Modifier
                    .fillMaxSize()) { innerPadding ->
                    ListDetailLayout(listOfApps = myList, listOfImages = drawables, modifier = Modifier.padding(innerPadding), weatherViewModel = weatherViewModel)
                }
            }
        }
    }

    private fun saveEncryptedDataInFile(context: Context?, cryptoManager: CryptoManager) {
        val file = File(filesDir, "secret.txt")
        if (!file.exists()) {
            filesDir.mkdirs()
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        val encryptedApiKey = cryptoManager.encrypt(
            bytes = Constants.API_KEY.encodeToByteArray(),
            outputStream = fos
        ).decodeToString()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailLayout(listOfApps: List<String>, listOfImages: List<Int>, modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel){

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            ListView(items = listOfApps, images = listOfImages, navigator)
        },
        detailPane = {
            AnimatedPane {
                DetailView(navigator = navigator, weatherViewModel = weatherViewModel)
            }
        })
}

@Composable
fun ListView(items: List<String>, images: List<Int>, navigator: ThreePaneScaffoldNavigator<Any>){

    val appItems = items.zip(images) { name, icon -> AppItem(name, icon) }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(16.dp)
    ){
        items(appItems) { appItem ->
            AppRow(appItem, navigator)
        }
    }
}

@Composable
fun DetailView(navigator: ThreePaneScaffoldNavigator<Any>, weatherViewModel: WeatherViewModel){

    val content = navigator.currentDestination?.content?.toString() ?: "Select an app"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        when(content){
            "Weather App" -> WeatherPage(weatherViewModel)
            /*"ToDo App Simple" -> ToDoAppSimple()
            "ToDo App Complex" -> ToDoAppComplex()*/
        }
    }
}

@Composable
fun AppRow(appItem: AppItem, navigator: ThreePaneScaffoldNavigator<Any>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navigator.navigateTo(
                    pane = ListDetailPaneScaffoldRole.Detail,
                    content = appItem.name
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = appItem.iconResId),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = appItem.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

data class AppItem(val name: String, val iconResId: Int)