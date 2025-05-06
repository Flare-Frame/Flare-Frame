package com.flareframe.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.flareframe.ui.states.LoginState
import com.flareframe.viewmodels.LoginViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
) {
    val uiState: LoginState by loginViewModel.uiState.collectAsState()


}