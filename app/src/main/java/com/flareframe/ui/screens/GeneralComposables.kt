@file:OptIn(ExperimentalMaterial3Api::class)

package com.flareframe.ui.screens

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

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
fun InputText(
    modifier: Modifier = Modifier,
    label: String,
    onTextUpdate: (String) -> Unit,
    text: String,
    imageVector: ImageVector?,

    ) {
    val gradientColors =
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val brush = remember { Brush.linearGradient(colors = gradientColors) }
    OutlinedTextField(
        value = text,
        maxLines = 1,
        placeholder = { Text(label) },
        onValueChange = { newVal -> onTextUpdate(newVal) },
        modifier = modifier,
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
fun PassworInputText(
    modifier: Modifier = Modifier,
    label: String,
    onTextUpdate: (String) -> Unit,
    text: String,

    ) {
    val gradientColors =
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val brush = remember { Brush.linearGradient(colors = gradientColors) }
    OutlinedTextField(
        value = text,
        maxLines = 1,
        placeholder = { Text(label) },
        onValueChange = { newVal -> onTextUpdate(newVal) },
        modifier = modifier,
        textStyle = TextStyle(brush = brush),
        visualTransformation = PasswordVisualTransformation(), // this is how to give it the look of a password
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),


        )
}
