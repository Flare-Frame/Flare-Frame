package com.flareframe.ui.screens.upload

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBackIosNew
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.flareframe.viewmodels.UploadPostViewModel

@Composable
fun MediaPreviewScreen(
    modifier: Modifier = Modifier, viewModel: UploadPostViewModel,
    onBackToCamera: () -> Unit,
    onNavigateToUpload: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.secondary,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Image Preview",
            modifier = Modifier.padding(top=50.dp, bottom=80.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = animatedColor
        )

        AsyncImage(
            model = if (uiState.uploadUri != null) {
                uiState.uploadUri
            } else if (uiState.pictureTaken != null) {
                uiState.pictureTaken
            } else {
                Text(
                    text = "There is nothing to display here"
                )

            },
            contentDescription = null,
            modifier = Modifier
                .size(320.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(vertical = 50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(onClick = {
                viewModel.resetImages()
                onBackToCamera()
            }) {
                Icon(Icons.TwoTone.ArrowBackIosNew, "Back to camera")
                Text(text = "Back to Camera")
            }
            FilledTonalButton(onClick = { onNavigateToUpload() }) { Text(text = "Continue") }
        }

    }
}