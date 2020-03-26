/*
MIT License

Copyright (c) 2020 pawelkon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.pawelkon.flashfeatures

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pawelkon.flashfeatures.light.flash.FlashAPI23
import kotlinx.android.synthetic.main.activity_flash_api23_test.*

/**
 * Manual test of FlashAPI23.
 */
class FlashAPI23Test : AppCompatActivity() {

    lateinit var cameraManager: CameraManager

    lateinit var torchCallback: CameraManager.TorchCallback

    lateinit var flash: FlashAPI23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_api23_test)

        cameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        torchCallback = object: CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                super.onTorchModeChanged(cameraId, enabled)
                lbl_modable_cameramanager_current_mode.text = enabled.toString()
            }
        }

        cameraManager.registerTorchCallback(torchCallback, null)

        flash = FlashAPI23(cameraManager)

        btn_turn_on.setOnClickListener {
            flash.setMode(true)
        }

        btn_turn_off.setOnClickListener {
            flash.setMode(false)
        }

        btn_update_mode.setOnClickListener {
            lbl_modable_flashapi23_current_mode.text = flash.mode().toString()
        }

        btn_update_mode.performClick()

    }

    override fun onDestroy() {
        super.onDestroy()

        flash.destroy()

        cameraManager.unregisterTorchCallback(torchCallback)
    }
}