/*
* Copyright (C) 2020 pawelkon
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the
* Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
* and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
* ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
* THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/

package com.pawelkon.flashfeatures.light.flash

import com.pawelkon.flashfeatures.light.Light

import android.hardware.camera2.CameraManager

/**
 * This class provides the functions of camera flash.
 * Allows you to enable/disable the flash and check the current mode.
 *
 * Minimal API - 23
 *
 * @constructor Creates a camera flash manager.
 * @param cameraManager CameraManager instance from the System service.
 * @param cameraId (optional)the number of the camera that has flash. "0" is default.
 */
class FlashAPI23(private val cameraManager: CameraManager, private val cameraId: String = "0")
    : Light {

    private var mode: Boolean? = null

    private lateinit var callback: CameraManager.TorchCallback

    init {
        registerModeCallback()
    }

    /**
     * Returns the current flash mode.
     *
     * @return **true** if the flash is enabled, **false** - disabled, or **null** if the mode is unknown.
     */
    override fun mode() : Boolean? {
        return mode
    }

    /**
     * Enables/disables the flash.
     *
     * @param mode **true** - turning on, **false** - turning off
     */
    override fun setMode(mode: Boolean) {
        cameraManager.setTorchMode(cameraId, mode)
        this.mode = mode
    }

    /**
     * it must be called before the object will be destroyed.
     */
    fun destroy() {
        cameraManager.unregisterTorchCallback(callback)
    }

    private fun registerModeCallback() {
        callback = ModeCallback()
        cameraManager.registerTorchCallback(callback, null)
    }

    private inner class ModeCallback() : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            super.onTorchModeChanged(cameraId, enabled)
            mode = enabled
        }
    }
}