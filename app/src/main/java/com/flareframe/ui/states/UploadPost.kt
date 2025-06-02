package com.flareframe.ui.states

import android.graphics.Bitmap
import android.net.Uri

data class UploadPost(
    val pictureTaken: Bitmap? = null,
    val uploadUri: Uri? = null, // use viewmodel to display tags
    val hideTags:Boolean = false,  // use a switch to toggle this value
    val currentTag:String = "",

    val tagErrorMessage:String = "",
    val captionErrorMessage:String = "",
    val pictureErrorMessage:String = "",
    val errorMessage:String = "",

    val isUploaded: Boolean = false,
    val isLoading:Boolean = false,
    val caption:String  = ""
    )
