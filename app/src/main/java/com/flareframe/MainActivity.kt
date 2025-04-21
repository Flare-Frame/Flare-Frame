package com.flareframe


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flareframe.ui.screens.AppButton
import com.flareframe.ui.screens.LoginScreen
import com.flareframe.ui.screens.RegisterScreen
import com.flareframe.ui.theme.FlareFrameTheme
import com.flareframe.viewmodels.RegistrationViewModel
import com.flareframe.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
    object Settings

    val viewmodel: RegistrationViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlareFrameTheme {   // scaffolds are used to show navigation bars and snackbars

                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()  // this is how to clear any snackbars
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke() // this will call for the action to happen if clicked
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
                    val navController =
                        rememberNavController()        // make deafult home page if logged in
                    NavHost(navController = navController, startDestination = Register) {
                        composable<Login> {
                            LoginScreen(
                                modifier = Modifier.padding(innerPadding),
                                userViewModel = userViewModel,
                                onLogin = {},
                                onRegister = {navController.navigate(route = Register)})   // add logic for home page
                        }

                        composable<Register> {
                            RegisterScreen(
                                modifier = Modifier,
                                viewModel = viewmodel,
                                onNavigateToLogin = {
                                    navController.navigate(route = Login)
                                },
                                haveAnAccount = { navController.navigate(route = Login) }

                            )  // add logic for home page
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlareFrameTheme {
        AppButton(text = "jk", onClick = {})
    }
}