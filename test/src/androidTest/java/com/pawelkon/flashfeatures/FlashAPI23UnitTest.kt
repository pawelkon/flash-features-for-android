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

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Unit tests for FlashAPI23.
 */
@RunWith(AndroidJUnit4::class)
class FlashAPI23UnitTest {

    /**
     * change the mode to true and check the result.
     */
    @Test
    fun setModeTrue() {
        val flashActivity = ActivityScenario.launch(FlashAPI23Test::class.java)
        flashActivity.onActivity {
                activity ->
            activity.flash.setMode(true)
            assertEquals(true, activity.flash.mode())
        }
    }

    /**
     * change the mode to false and check the result.
     */
    @Test
    fun setModeFalse() {
        val flashActivity = ActivityScenario.launch(FlashAPI23Test::class.java)
        flashActivity.onActivity {
                activity ->
            activity.flash.setMode(false)
            assertEquals(false, activity.flash.mode())
        }
    }

    /**
     * test checks reaction for change the mode to true by CameraManager instance.
     * Testing the default cameraId - "0"
     */
    @Test
    fun setTorchModeTrue() {
        var mode: Boolean? = null
        val flashActivity = ActivityScenario.launch(FlashAPI23Test::class.java)
        flashActivity.onActivity {
                activity ->
            activity.cameraManager.setTorchMode("0", true)
        }
        flashActivity.onActivity {
                activity ->
            mode = activity.flash.mode()
        }
        assertEquals(true, mode)
    }

    /**
     * test checks reaction for change the mode to true by CameraManager instance.
     * Testing the default cameraId - "0"
     */
    @Test
    fun setTorchModeFalse() {
        var mode: Boolean? = null
        val flashActivity = ActivityScenario.launch(FlashAPI23Test::class.java)
        flashActivity.onActivity {
                activity ->
            activity.cameraManager.setTorchMode("0", false)
        }
        flashActivity.onActivity {
                activity ->
            mode = activity.flash.mode()
        }
        assertEquals(false, mode)
    }
}