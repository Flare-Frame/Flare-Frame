package com.flareframe.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flareframe.MainActivity.Home
import com.flareframe.MainActivity.Loading
import com.flareframe.MainActivity.Login
import com.flareframe.MainActivity.Register
import com.flareframe.ObserveAsEvents
import com.flareframe.SnackbarController
import com.flareframe.viewmodels.AuthViewModel
import com.flareframe.viewmodels.LoginViewModel
import com.flareframe.viewmodels.RegistrationViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Composable
fun AppScaffold(
    loginViewModel: LoginViewModel,
    registrationViewModel: RegistrationViewModel,
    authViewModel: AuthViewModel,
) {
    val navController =
        rememberNavController()        // make deafult home page if logged in

    // scaffolds are used to show navigation bars and snackbars
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val userState by authViewModel.userState.collectAsStateWithLifecycle()
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
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    LaunchedEffect(userState.isLoggedIn, userState.isLoading) {

        if (userState.isLoading) {
            navController.navigate(route = Loading)
        } else {
            if (userState.isLoggedIn) {

                navController.navigate(route = Home)
            } else if (!userState.isLoggedIn && currentDestination != Login) {

                navController.navigate(Login) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }

    }
    val showBottomBar = when (currentDestination?.route) {
        Login::class.qualifiedName,
        Register::class.qualifiedName,
            -> false

        else -> true
    }
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(navController = navController)
            }

        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable<Login> {
                LoginScreen(
                    modifier = Modifier,
                    userViewModel = loginViewModel,
                    onNavigateToHome = {
                        navController.navigate(route = Home)
                    },
                    onRegister = { navController.navigate(route = Register) })   // add logic for home page
            }
            composable<Loading> { LoadingScreen() }

            composable<Register> {
                RegisterScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = registrationViewModel,
                    onNavigateToLogin = {
                        navController.navigate(route = Login){
                            popUpTo(0){inclusive=true}
                        }
                    },


                )  // add logic for home page
            }
            composable<Home> {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    loginViewModel = loginViewModel,
                )
            }

        }
    }
}