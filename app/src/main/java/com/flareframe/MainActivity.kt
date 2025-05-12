package com.flareframe


import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.flareframe.services.Camera

import com.flareframe.ui.screens.AppScaffold

import com.flareframe.ui.theme.FlareFrameTheme
import com.flareframe.viewmodels.AuthViewModel
import com.flareframe.viewmodels.LoginViewModel
import com.flareframe.viewmodels.RegistrationViewModel
import com.flareframe.viewmodels.UploadPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
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

    @Serializable
    object CameraScreen
    @Serializable
    object MediaPreview

    val viewmodel: RegistrationViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val uploadPostViewModel: UploadPostViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this,
                Camera.CAMERAX_PERMISSIONS,
                0  // we are setting it to default that they allow it
            )
        }

        setContent {
            FlareFrameTheme {

                // defaults to the one on the left, first one

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppScaffold(loginViewModel, viewmodel, authViewModel,uploadPostViewModel, applicationContext)
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {     // we need to check if accepted
        return Camera.CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext!!,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlareFrameTheme {

    }
}
