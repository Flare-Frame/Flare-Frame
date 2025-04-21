package com.flareframe.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flareframe.SnackbarController
import com.flareframe.SnackbarEvent
import com.flareframe.ui.states.RegisterUiState

import com.flareframe.ui.states.UserUiState
import com.flareframe.viewmodels.UserViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, userViewModel: UserViewModel = hiltViewModel(), onLogin:()-> Unit,onRegister:()-> Unit){
   val uiState: UserUiState by userViewModel.uiState.collectAsStateWithLifecycle()

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.secondary,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    Box(modifier.fillMaxSize()) {

        if (uiState.inProgress)
            CircularProgressIndicator(Modifier.align(Alignment.Center))

    }
    if(uiState.errorMessage.isNotEmpty()){
        LaunchedEffect(uiState.errorMessage) {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = uiState.errorMessage
                )
            )
        }
    }
    if (uiState.isLoggedin == true) {

        LaunchedEffect(uiState.isLoggedin) {
            // show a snack bar
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "Welcome back ${uiState.username}"
                )
            )
            onLogin // add a real navigation here
        }
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,  modifier = Modifier
        .fillMaxSize()) {
        Text(
            text = "Flare Frame",
            modifier = Modifier.padding(bottom = 60.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = animatedColor,
        )
      InputText(
          modifier = Modifier.padding(bottom = 25.dp),
          label = "Email",
          onTextUpdate = {newText-> userViewModel.onEmailValueChange(newText)},
          text = uiState.username,
          imageVector = Icons.Outlined.AccountCircle,
      )
        PassworInputText(
            modifier = Modifier.padding(bottom = 25.dp),
            label = "Password",
            onTextUpdate = {newText-> userViewModel.onPasswordValueChange(newText)},
            text = uiState.password,


        )
        Spacer(Modifier.padding(vertical = 20.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(0.78f),
            text = "Login"
        ) {

            userViewModel.onLogin()
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.78f),
            onClick = {

                onRegister()
            }
        ){
            Text(text = "Don't have an account")
        }

        Spacer(Modifier.padding(vertical = 40.dp))

        Text(
            text = "By Shravan Ramjathan",
            modifier = Modifier.clickable(enabled = true, onClick = {})
        )
    }
}