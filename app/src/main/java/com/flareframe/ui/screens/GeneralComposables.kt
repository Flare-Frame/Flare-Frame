@file:OptIn(ExperimentalMaterial3Api::class)

package com.flareframe.ui.screens

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Cancel
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.flareframe.MainActivity.Account
import com.flareframe.MainActivity.Home
import com.flareframe.MainActivity.Search
import com.flareframe.MainActivity.Upload
import com.flareframe.ui.navBar.BottomNavigationItem
import kotlinx.serialization.ExperimentalSerializationApi

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    var primaryColor = MaterialTheme.colorScheme.primaryContainer
    val secondaryColor = MaterialTheme.colorScheme.onPrimaryContainer
    ElevatedButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = secondaryColor

        )

    ) {
        Text(
            text = text
        )


    }
}

@Composable
fun HashTagChip(
    hashTag: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InputChip(
        onClick = { onRemove() },
        label = { Text(hashTag) },
        modifier = modifier,
        selected = true,

        trailingIcon = {
            Icon(Icons.TwoTone.Cancel, contentDescription = "Cancel button")

        }
    )
}

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    label: String,
    imageVector: ImageVector?,
    contentType: ContentType,
    inputState: TextFieldState,
) {
    val gradientColors =
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val brush = remember { Brush.linearGradient(colors = gradientColors) }
    OutlinedTextField(
        state = inputState,
        placeholder = { Text(label) },
        lineLimits = TextFieldLineLimits.SingleLine,  // new way to limit line size
        modifier = modifier.semantics { this.contentType = contentType },
        textStyle = TextStyle(brush = brush),
        trailingIcon = {
            Icon(
                imageVector = imageVector!!,
                contentDescription = "Account",

                )
        }

    )
}

@Composable
fun PasswordInputText(
    modifier: Modifier = Modifier,
    label: String,
    inputState: TextFieldState,
    contentType: ContentType,
) {

    val gradientColors =
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val brush = remember { Brush.linearGradient(colors = gradientColors) }
    OutlinedSecureTextField(
        state = inputState,
        placeholder = { Text(label) },
        modifier = modifier.semantics { contentType },
        textStyle = TextStyle(brush = brush),
        // this is how to give it the look of a password


    )
}

@ExperimentalSerializationApi
@Composable
fun AppBottomBar(navController: NavController) {
    // createing a list of nav items
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            route = Home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNotis = false,
            badgeCount = null
        ),
        BottomNavigationItem(
            title = "Search",
            route = Search,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNotis = false,
            badgeCount = null
        ),
        BottomNavigationItem(
            title = "Upload",
            route = Upload,
            selectedIcon = Icons.Filled.AddCircle,
            unselectedIcon = Icons.Outlined.Add,
            hasNotis = false,
            badgeCount = null
        ),
        BottomNavigationItem(
            title = "Account",
            route = Account,
            selectedIcon = Icons.Filled.AccountBox,
            unselectedIcon = Icons.Outlined.AccountBox,
            hasNotis = false,
            badgeCount = null
        )
    )
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        }, contentDescription = item.title
                    )
                }, alwaysShowLabel = false,
                label = { Text(text = item.title) }
            )
        }
    }
}

