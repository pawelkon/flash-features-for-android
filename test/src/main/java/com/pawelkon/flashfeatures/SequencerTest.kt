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
import com.pawelkon.flashfeatures.light.sequencer.LightSequenceMaker
import com.pawelkon.flashfeatures.sequencer.SequenceExecutor
import kotlinx.android.synthetic.main.activity_sequencer_test.*

/**
 * Manual test of Sequencer.
 */
class SequencerTest : AppCompatActivity() {

    private var flash: FlashAPI23? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequencer_test)

        btn_sos.setOnClickListener {
            val cameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            flash = FlashAPI23(cameraManager)

            val sequenceMaker = LightSequenceMaker(flash!!);

            flash!!.setMode(false)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)
            sequenceMaker.flash(600)
            sequenceMaker.interval(100)
            sequenceMaker.flash(600)
            sequenceMaker.interval(100)
            sequenceMaker.flash(600)
            sequenceMaker.interval(100)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)
            sequenceMaker.flash(300)
            sequenceMaker.interval(100)

            val sequence = sequenceMaker.getSequence()
            val sequenceExecutor = SequenceExecutor(sequence)
            sequenceExecutor.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flash?.destroy()
        flash = null
    }
}
