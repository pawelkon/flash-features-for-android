package com.example.flashlightfeatures

import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flashlightBuilder = FlashlightAPI23Builder(this.applicationContext)
        var flashlight = flashlightBuilder.getFlashlight()
        Thread.sleep(1000)
        Toast.makeText(this.applicationContext, flashlight.mode().toString(), Toast.LENGTH_SHORT).show()
        flashlight.setMode(true)
        Toast.makeText(this.applicationContext, flashlight.mode().toString(), Toast.LENGTH_SHORT).show()
    }
}
