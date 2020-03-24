package com.pawelkon.flashfeatures

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pawelkon.flashfeatures.light.flash.FlashAPI23
import com.pawelkon.flashfeatures.light.sequencer.LightSequenceMaker
import com.pawelkon.flashfeatures.sequencer.SequenceExecutor
import kotlinx.android.synthetic.main.activity_sequencer_test.*

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
