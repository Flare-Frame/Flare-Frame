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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flareframe.models.Post
import com.flareframe.repositories.PostRepository
import com.flareframe.repositories.UserRepository
import com.flareframe.ui.states.UploadPost
import com.flareframe.ui.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UploadPostViewModel @Inject constructor(
    val userRepository: UserRepository,
    val postRepository: PostRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UploadPost())
    val uiState: StateFlow<UploadPost> = _uiState.asStateFlow()
    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags.asStateFlow()

    fun updateCaption(caption: String) {
        _uiState.update {
            it.copy(caption = caption, captionErrorMessage = "")
        }
    }

    fun updateTags() {
        if (_uiState.value.currentTag.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(tagErrorMessage = "Can't enter an empty tag.")
            }
            return
        }

        _tags.update { currentList ->
            currentList + _uiState.value.currentTag          // display this using chips that has a X button to remove them
        }
        _uiState.update {
            it.copy(currentTag = "")
        }
    }


    fun updateCurrentTag(currTag: String) {
        _uiState.update {
            it.copy(currentTag = currTag, tagErrorMessage = "")
        }
    }

    fun removeTag(tagPos: Int) {
        _tags.update { currentList ->
            currentList.toMutableList().apply {
                if (tagPos in indices) removeAt(tagPos)
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

    fun uploaded() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = false, isUploaded = true)
        }
    }

    fun uploadFailed(errorMessage:String) {
        _uiState.update {
            it.copy(isLoading = false, errorMessage = errorMessage)

        }
    }

    fun makePost(userState: UserState) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }
//        val username = userRepos
        if (_uiState.value.caption.isEmpty()) {Log.d("Upload","Add a caption.")
            _uiState.update { currentState ->
                currentState.copy(captionErrorMessage = "Please add a caption.", isLoading = false)

            }

            return
        }
        if (_tags.value.count() == 0) {
            Log.d("Upload","Enter tags.")
            _uiState.update { currentState ->
                currentState.copy(tagErrorMessage = "Please enter tags.", isLoading = false)
            }
            Log.d("Upload","Please enter tags.")
            return
        }
        if (_uiState.value.pictureTaken == null && _uiState.value.uploadUri == null) {
            _uiState.update { currentState ->
                currentState.copy(pictureErrorMessage = "There is no image.", isLoading = false)

            }
            Log.d("Upload","There is no image.")
            return
        }
        val post: Post = Post(
            archived = false,
            hideTags = false,
            username = userState.username,
            caption = _uiState.value.caption,
            postId = UUID.randomUUID().toString(),
            archivedAt = null
        )
        viewModelScope.launch {
            Log.d("Upload","Uploading...")
            val result: Result<Post> = postRepository.createPost(post)
            if (result.isSuccess) {
                Log.d("Upload","Uploaded...")
                uploaded()
            } else {
               val errorMessage:String = result.exceptionOrNull().toString()
                Log.d("Upload",errorMessage)
                uploadFailed(errorMessage)
            }
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