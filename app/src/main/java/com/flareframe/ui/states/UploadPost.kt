package com.flareframe.ui.states

import android.graphics.Bitmap
import android.net.Uri
import com.flareframe.models.Tags

data class UploadPost(
    val pictureTaken: Bitmap? = null,
    val uploadUri: Uri? = null, // use viewmodel to display tags
    val hideTags:Boolean = false,  // use a switch to toggle this value
    val currentTag:String = "",
    val isLoading:Boolean = false,
    val caption:String  = ""
    )
