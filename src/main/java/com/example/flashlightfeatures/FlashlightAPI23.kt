package com.example.flashlightfeatures

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Handler

class FlashlightAPI23 : Flashlight {

    private val cameraManager: CameraManager

    private val cameraId: String

    private var mode: Boolean? = null

    constructor(cameraManager: CameraManager, cameraId: String) {
        this.cameraManager = cameraManager
        this.cameraId = cameraId
        registerModeCallback()
    }

    override fun mode() : Boolean? {
        return mode
    }

    override fun setMode(enabled: Boolean): Boolean? {
        cameraManager.setTorchMode(cameraId, enabled)
        mode = enabled
        return mode
    }

    fun registerCallback(callback: CameraManager.TorchCallback, handler: Handler?) {
        cameraManager.registerTorchCallback(callback, handler)
    }

    fun unregisterCallback(callback: CameraManager.TorchCallback) {
        cameraManager.unregisterTorchCallback(callback)
    }

    private fun registerModeCallback() {
        registerCallback(ModeCallback(this), null)
    }

    private inner class ModeCallback(private val flashlight: Flashlight)
        : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            super.onTorchModeChanged(cameraId, enabled)
            mode = enabled
        }
    }
}