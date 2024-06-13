@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3AdaptiveApi::class
)

package com.sreejithsnair.skysense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sreejithsnair.skysense.ui.theme.SkySenseTheme
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.utilities.CryptoManager
import com.sreejithsnair.skysense.viewmodel.ToDoViewModel
import com.sreejithsnair.skysense.viewmodel.WeatherViewModel
import com.sreejithsnair.skysense.views.common.GenericCardContent
import com.sreejithsnair.skysense.views.profile.ProfilePage
import com.sreejithsnair.skysense.views.todoapp.ToDoListPage
import com.sreejithsnair.skysense.views.weatherapp.WeatherPage
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherViewModel = ViewModelProvider(owner = this)[WeatherViewModel::class.java]
        val toDoViewModel = ViewModelProvider(owner = this)[ToDoViewModel::class.java]

        val myList = listOf("My Profile", getString(R.string.weather_app_title), "ToDo App", "ToDo App Complex")
        val drawables = listOf(R.drawable.profile_picture,R.drawable.weather_app_icon, R.drawable.todo_app_icon, R.drawable.weather_app_bg)

        val appItems = myList.zip(drawables) { name, icon -> AppItem(name, icon) }

        val cryptoManager = CryptoManager()

        setContent {
            SkySenseTheme {
                saveEncryptedDataInFile(cryptoManager = cryptoManager)
                Scaffold(modifier = Modifier
                    .fillMaxSize()) { innerPadding ->
                    ActivityBgImageSetter()
                    ListDetailLayout(appItems, modifier = Modifier.padding(innerPadding), weatherViewModel = weatherViewModel, toDoViewModel = toDoViewModel)
                }
            }
        }
    }

    private fun saveEncryptedDataInFile(cryptoManager: CryptoManager) {
        val file = File(filesDir, "secret.txt")
        if (!file.exists()) {
            filesDir.mkdirs()
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        /*val encryptedApiKey = cryptoManager.encrypt(
            bytes = Constants.API_KEY.encodeToByteArray(),
            outputStream = fos
        ).decodeToString()*/
    }
}

@Composable
fun TitleCard(){
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = false
    )

    systemUiController.setNavigationBarColor(
        color = Color.Transparent,
        darkIcons = false
    )

    GenericCardContent("App Hub", Constants.appHubDescription)

}


@Composable
fun ActivityBgImageSetter(){
    val backgroundImage = painterResource(id = R.drawable.weather_app_bg)

    Image(painter = backgroundImage,
        contentDescription = "background image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ProfileCard() {
    var showProfile by remember { mutableStateOf(false) }
    val name = "Sreejith S Nair"
    val designation = "Senior Software Engineer - Android"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0x24000000))
            .padding(vertical = 8.dp)
            .clickable {
                showProfile = !showProfile
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(

            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if(showProfile) 136.dp else 96.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                if(!showProfile){
                    Text(
                        text = "My Profile",
                        fontSize = 20.sp,
                        fontFamily = Constants.customFontFamily,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(){
                    if(showProfile){
                        NormalText(name, isName = true)
                        Spacer(modifier = Modifier.size(8.dp))
                        NormalText(designation, isName = false)
                    }
                }

            }
            if(showProfile) ProfilePage()
        }
    }
}

@Composable
fun NormalText(text: String, isName: Boolean = false ){
    if(isName) {
        Text(
            text = text,
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White,
        )
    } else {
        Text(
            text = text,
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailLayout(appItems: List<AppItem>, modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel, toDoViewModel: ToDoViewModel){

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            ListView(appItems, navigator)
        },
        detailPane = {
            AnimatedPane {
                DetailView(navigator = navigator, weatherViewModel = weatherViewModel, toDoViewModel = toDoViewModel)
            }
        })
}

@Composable
fun ListView(appItems: List<AppItem>, navigator: ThreePaneScaffoldNavigator<Any>){



    Column {
        Spacer(modifier = Modifier.size(8.dp))
        TitleCard()
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(appItems) { appItem ->
                AppRow(appItem, navigator)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

    }
}

@ExperimentalMaterial3AdaptiveApi
@Composable
fun DetailView(navigator: ThreePaneScaffoldNavigator<Any>, weatherViewModel: WeatherViewModel, toDoViewModel: ToDoViewModel){

    val content = navigator.currentDestination?.content?.toString() ?: "Select an app"

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(content){
            "Weather App" -> WeatherPage(weatherViewModel)
            "ToDo App" -> ToDoListPage(toDoViewModel)
        }
    }
}

@Composable
fun AppRow(appItem: AppItem, navigator: ThreePaneScaffoldNavigator<Any>) {

    if(appItem.name == "My Profile"){
        ProfileCard()
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0x24000000))
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
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = appItem.name,
                fontSize = 20.sp,
                fontFamily = Constants.customFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class AppItem(val name: String, val iconResId: Int)