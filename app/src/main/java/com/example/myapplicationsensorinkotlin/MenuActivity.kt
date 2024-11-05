package com.example.myapplicationsensorinkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val buttonMotionEvent: Button = findViewById(R.id.button_motion_event)
        val buttonSensors: Button = findViewById(R.id.button_sensors)

        buttonMotionEvent.setOnClickListener {
            val intent = Intent(this, MotionEventActivity::class.java)
            startActivity(intent)
        }

        buttonSensors.setOnClickListener {
            val intent = Intent(this, SensorActivity::class.java)
            startActivity(intent)
        }
    }
}