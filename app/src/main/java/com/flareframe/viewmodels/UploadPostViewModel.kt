package com.flareframe.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.flareframe.repositories.UserRepository
import com.flareframe.ui.states.UploadPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UploadPostViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UploadPost())
    val uiState: StateFlow<UploadPost> = _uiState.asStateFlow()
    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags.asStateFlow()

    fun updateCaption(caption: String) {
        _uiState.update {
            it.copy(caption = caption)
        }
    }

    fun updateTags() {
        _tags.update { currentList ->
            currentList + _uiState.value.currentTag          // display this using chips that has a X button to remove them
        }
        _uiState.update {
            it.copy(currentTag = "")
        }
    }


    fun updateCurrentTag(currTag:String){
        _uiState.update {
            it.copy(currentTag = currTag)
        }
    }
    fun removeTag(tagPos:Int){
        _tags.update {currentList ->
            currentList.toMutableList().apply {
                if(tagPos in indices)removeAt(tagPos)
            }
        }
    }
    fun galleryPicker(uri: Uri?) {
        _uiState.update {
            it.copy(uploadUri = uri, pictureTaken = null)
        }
    }

    fun resetImages() {
        _uiState.update {
            it.copy(uploadUri = null, pictureTaken = null, isLoading = true)

        }
    }

    fun stopLoading() {
        _uiState.update {
            it.copy(isLoading = false)

        }
    }

    // below is all to take a photo
    fun takePhoto(
        controller: LifecycleCameraController,

        applicationContext: Context,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object :
                OnImageCapturedCallback() {    // click ctrl +o which will get teh override for this message
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply {
                        if (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                            postScale(-1f, 1f)
                        }
                    }
                    val newImage = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    _uiState.update {
                        it.copy(pictureTaken = newImage, uploadUri = null)
                    }
                    Log.w("Camera", "WE HAVE A PICTURE HERE")
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo", exception)
                }

            }


        )
    }

}