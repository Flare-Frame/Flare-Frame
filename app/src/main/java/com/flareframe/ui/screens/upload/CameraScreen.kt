package com.flareframe.ui.screens.upload

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flareframe.viewmodels.UploadPostViewModel

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadPostViewModel,
    context: Context,
    onPictureTaken: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE)
        }
    }
    LaunchedEffect(uiState.pictureTaken) {
        if (uiState.pictureTaken != null) {
            onPictureTaken()

        }
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        // ADD BUTTONS TO TAKE PIC AND USE VIEWMODEL TO HOLD IMAGE

        CameraPreview(
            controller = controller,
            modifier = Modifier.padding(top = 150.dp)
                .size(300.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.BottomCenter).padding(bottom = 45.dp)
               ,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch, "Camera Switch"
                )
            }
            IconButton(
                onClick = {
                    viewModel.takePhoto(
                        controller = controller,
                        applicationContext = context
                    )
                },
                modifier = Modifier
            ) {

                Icon(
                    imageVector = Icons.Default.Camera, "Camera Switch"
                )
            }


        }
    }
}