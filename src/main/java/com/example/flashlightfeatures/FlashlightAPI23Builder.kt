package com.example.flashlightfeatures

import android.content.Context
import android.hardware.camera2.CameraManager

class FlashlightAPI23Builder(private val context: Context) {

    private var cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId = "0"

    fun setCameraManager(cameraManager: CameraManager) {
        this.cameraManager = cameraManager
    }

    fun setCameraManager(context: Context) {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    fun setCameraId(cameraId: String) {
        this.cameraId = cameraId
    }

    fun getFlashlight(): FlashlightAPI23 {
        return FlashlightAPI23(cameraManager, cameraId)
    }
}