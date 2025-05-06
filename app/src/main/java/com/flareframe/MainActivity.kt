package com.flareframe


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.flareframe.ui.screens.AppButton
import com.flareframe.ui.screens.AppScaffold
import com.flareframe.ui.theme.FlareFrameTheme
import com.flareframe.viewmodels.AuthViewModel
import com.flareframe.viewmodels.RegistrationViewModel
import com.flareframe.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Serializable
    object Register

    @Serializable
    object Login

    @Serializable
    object Home

    @Serializable
    object Account

    @Serializable
    object Upload

    @Serializable
    object Search

    @Serializable
    object Loading
    val viewmodel: RegistrationViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlareFrameTheme {

                // defaults to the one on the left, first one

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppScaffold(loginViewModel, viewmodel, authViewModel)
                }
            }
        }
    }

}
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        FlareFrameTheme {
            AppButton(text = "jk", onClick = {})
        }
    }