package com.sreejithsnair.skysense.views.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.sreejithsnair.skysense.R
import com.sreejithsnair.skysense.utilities.Constants

@Composable
fun ProfilePage() {
    val context = LocalContext.current

    val email = "sreejithsnair.sj@gmail.com"
    val phoneNumber = "8848791837"
    val linkedinUrl = "https://www.linkedin.com/in/yourprofile"
    val googleDeveloperProfileUrl = "https://developers.google.com/profile/u/yourprofile"
    val githubUrl = "https://github.com/yourprofile"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = email,
                icon = R.drawable.mail,
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(email)
                    }
                    try {
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "No app found to handle this action", Toast.LENGTH_SHORT).show()
                    }


                }
            )
            ClickableText(
                text = phoneNumber,
                icon = R.drawable.mail,
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${phoneNumber}")
                    }
                    try {
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "No app found to handle this action", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinkText("Google Developer Profile", R.drawable.google, googleDeveloperProfileUrl)
            LinkText("LinkedIn", R.drawable.linkedin, linkedinUrl)
            LinkText("Github", R.drawable.github, githubUrl)
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = "Resume",
                icon = R.drawable.mail,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://yourwebsite.com/resume.pdf")
                    }
                    startActivity(context, intent, null)
                }
            )
        }
    }
}



@Composable
fun ClickableText(text: String, icon: Int, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun LinkText(text: String, icon: Int, url: String) {
    val context = LocalContext.current
    Row (
        modifier = Modifier
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, intent, null)
                }
        )
    }

}
