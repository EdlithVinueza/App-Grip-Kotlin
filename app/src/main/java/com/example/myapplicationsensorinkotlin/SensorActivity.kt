package com.example.myapplicationsensorinkotlin

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.os.Handler
import android.os.Looper

class SensorActivity : ComponentActivity(), SensorEventListener {

    // Declaración de variables para los elementos de la interfaz de usuario
    private lateinit var mainLayout: RelativeLayout
    private lateinit var accelerometerTextView: TextView
    private lateinit var gyroscopeTextView: TextView
    private lateinit var forceTextView: TextView
    private lateinit var proximityTextView: TextView


    // Declaración de variables para el manejo de sensores
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var pressureSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        // Inicialización de los elementos de la interfaz de usuario
        mainLayout = findViewById(R.id.sensorLayout)
        accelerometerTextView = findViewById(R.id.accelerometerTextView)
        gyroscopeTextView = findViewById(R.id.gyroscopeTextView)
        forceTextView = findViewById(R.id.forceTextView)
        proximityTextView = findViewById(R.id.proximityTextView)

        // Inicialización del sensor manager y los sensores
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    }

    override fun onResume() {
        super.onResume()
        // Registrar los listeners para los sensores
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        gyroscope?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        proximitySensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        pressureSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar los listeners para los sensores
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    // Obtener los valores del acelerómetro
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]
                    accelerometerTextView.text = "Acelerómetro: x=${x}m/s², y=${y}m/s², z=${z}m/s²"

                    // Calcular la fuerza y actualizar el TextView y el color de fondo
                    val force = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                    forceTextView.text = "Fuerza: ${force}N"
                    mainLayout.setBackgroundColor(obtenerColorSegunFuerza(force))
                }
                Sensor.TYPE_GYROSCOPE -> {
                    // Obtener los valores del giroscopio
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]
                    gyroscopeTextView.text = "Giroscopio: x=${x}rad/s, y=${y}rad/s, z=${z}rad/s"
                }
                Sensor.TYPE_PROXIMITY -> {
                    // Obtener el valor del sensor de proximidad
                    val proximity = it.values[0]
                    proximityTextView.text = "Proximidad: ${proximity}cm"
                    val color = obtenerColorSegunProximidad(proximity)
                    if (color != -1) {
                        mainLayout.setBackgroundColor(color)
                    }
                }
            }
        }
    }

    // Método para obtener el color basado en la fuerza
    private fun obtenerColorSegunFuerza(force: Float): Int {
        val color = when {
            force < 5 -> Color.GREEN
            force < 10 -> Color.TRANSPARENT
            force < 15 -> Color.BLUE
            else -> Color.RED
        }

        // Mantener el color en pantalla durante mas tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            mainLayout.setBackgroundColor(Color.TRANSPARENT)
        }, 5000)

        return color
    }


    // Método para obtener el color basado en la proximidad
    private fun obtenerColorSegunProximidad(proximity: Float): Int {
        return if (proximity < 5) Color.MAGENTA else -1
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No hacer nada
    }
}