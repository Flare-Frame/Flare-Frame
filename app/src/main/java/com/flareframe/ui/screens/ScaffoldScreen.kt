package com.flareframe.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flareframe.MainActivity.Home
import com.flareframe.MainActivity.Login
import com.flareframe.MainActivity.Register
import com.flareframe.ObserveAsEvents
import com.flareframe.SnackbarController
import com.flareframe.viewmodels.RegistrationViewModel
import com.flareframe.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Composable
fun AppScaffold(userViewModel: UserViewModel, registrationViewModel: RegistrationViewModel) {
    val navController =
        rememberNavController()        // make deafult home page if logged in

    // scaffolds are used to show navigation bars and snackbars
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
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val showBottomBar = when (currentDestination?.route) {
        Login::class.qualifiedName,
        Register::class.qualifiedName -> false
        else -> true
    }
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(navController = navController)
            }
//            AppBottomBar(
//                navController = navController
//            )
        },
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)}
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable<Login> {
                LoginScreen(
                    modifier = Modifier,
                    userViewModel = userViewModel,
                    onNavigateToHome = {
                        navController.navigate(route = Home)
                    },
                    onRegister = { navController.navigate(route = Register) })   // add logic for home page
            }

            composable<Register> {
                RegisterScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = registrationViewModel,
                    onNavigateToLogin = {
                        navController.navigate(route = Login)
                    },
                    haveAnAccount = { navController.navigate(route = Login) }

                )  // add logic for home page
            }
            composable<Home> {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    userViewModel = userViewModel,
                )
            }

        }
    }
}