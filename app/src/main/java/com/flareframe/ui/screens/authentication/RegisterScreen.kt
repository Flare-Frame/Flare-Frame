package com.flareframe.ui.screens.authentication

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flareframe.SnackbarController
import com.flareframe.SnackbarEvent
import com.flareframe.ui.screens.AppButton
import com.flareframe.ui.screens.InputText
import com.flareframe.ui.screens.PasswordInputText
import com.flareframe.ui.states.RegisterUiState
import com.flareframe.viewmodels.RegistrationViewModel


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,

    ) {
    val uiState: RegisterUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.secondary,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    val autofillManager = LocalAutofillManager.current
    Box(modifier.fillMaxSize()) {

        if (uiState.inProgress)
            CircularProgressIndicator(Modifier.align(Alignment.Center))

    }
    if (uiState.isRegistered == true) {

        LaunchedEffect(uiState.isRegistered) {
            // show a snack bar
            autofillManager?.commit()
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "You have successfully registered"
                )
            )
            viewModel.resetState()
            onNavigateToLogin()
        }
    }
    if (uiState.errorMessage.isNotBlank()) {
        LaunchedEffect(uiState.errorMessage) {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = uiState.errorMessage
                )
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()

    ) {

        Text(
            text = "Flare Frame",
            modifier = Modifier.padding(bottom = 60.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = animatedColor,
        )
        InputText(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .padding(bottom = 15.dp),

            label = "Username",
            imageVector = Icons.Outlined.AccountCircle,
            contentType = ContentType.NewUsername,
            inputState = viewModel.username,
        )

        InputText(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .padding(bottom = 15.dp),

            label = "Email",
            imageVector = Icons.Outlined.AccountCircle,
            contentType = ContentType.EmailAddress,
            inputState = viewModel.email,
        )
        PasswordInputText(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .padding(bottom = 15.dp),

            label = "Password",
            contentType = ContentType.NewPassword,
            inputState = viewModel.password,
        )
        PasswordInputText(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .padding(bottom = 40.dp),

            label = "Confirm Password",
            contentType = ContentType.Password,
            inputState = viewModel.confirmPassword,

            )
        AppButton(
            text = "Register",
            onClick = { viewModel.onRegister() },
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(0.78f)
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.78f),
            onClick = {

                onNavigateToLogin()
            }
        ) {
            Text(text = "Already have an account")
        }
        Spacer(Modifier.padding(30.dp))
        Text(
            text = "By Shravan Ramjathan",
            modifier = Modifier.clickable(enabled = true, onClick = {})
        )
    }
}



