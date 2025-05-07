package com.flareframe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.flareframe.ui.states.UserState
import com.flareframe.viewmodels.AuthViewModel

@Composable      // rename this
fun ProfileScreen(viewModel: AuthViewModel, modifier: Modifier = Modifier.fillMaxSize()) {
    val uiState: UserState by viewModel.userState.collectAsStateWithLifecycle() // add a new viewmodel for this
    UserFlameCon(
        "https://images.app.goo.gl/XqHXg4HvwRrAoXqp7",
        contentDescription = "sfsdf",
        username = uiState.username ,
        flames = 10,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )

}

@Composable
fun UserFlameCon(
    url: String,
    contentDescription: String,
    username: String,
    flames: Int,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
) {
    rememberConstraintsSizeResolver()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data("") // add a image here
            .crossfade(true)
            .build(),
    )


//
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
        )
        Column() {
            Text(text = username, modifier = Modifier.width(100.dp))
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Flames $flames", modifier = Modifier.width(100.dp))
                Text(text = " Followers 10", modifier = Modifier.width(100.dp)
                )
            }

        }


    }
}