package com.flareframe.services

import android.Manifest


object Camera {
   public   val CAMERAX_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,  // these are added in teh manifest
        Manifest.permission.RECORD_AUDIO
    )

}