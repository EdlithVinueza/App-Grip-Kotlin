package com.example.myapplicationsensorinkotlin

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

// Actividad para manejar eventos de movimiento
class MotionEventActivity : ComponentActivity() {

    // Declarar variables para el diseño principal y las vistas de texto
    private lateinit var layoutPrincipal: RelativeLayout
    private lateinit var textoPresion: TextView
    private lateinit var textoTamaño: TextView
    private lateinit var textoFuerza: TextView

    // Inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_event)
        layoutPrincipal = findViewById(R.id.motionEventLayout)
        textoPresion = findViewById(R.id.pressureTextView)
        textoTamaño = findViewById(R.id.sizeTextView)
        textoFuerza = findViewById(R.id.forceTextView) // Inicializar textoFuerza
    }

    // Manejar eventos táctiles
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_DOWN || it.action == MotionEvent.ACTION_MOVE) {
                val presion = it.pressure
                val tamaño = it.size
                val fuerza = presion * tamaño // Calcular la fuerza

                textoPresion.text = "Presión: $presion N/m²"
                textoTamaño.text = "Tamaño: $tamaño"
                textoFuerza.text = "Fuerza: $fuerza N" // Mostrar la fuerza con unidades

                val x = it.x
                val y = it.y
                val color = obtenerColorSegunPosicion(x, y)
                layoutPrincipal.setBackgroundColor(color)
            }
        }
        return super.onTouchEvent(event)
    }

    // Determinar el color basado en la posición del evento táctil
    private fun obtenerColorSegunPosicion(x: Float, y: Float): Int {
        return if (x < layoutPrincipal.width / 2 && y < layoutPrincipal.height / 2) {
            Color.RED
        } else if (x >= layoutPrincipal.width / 2 && y < layoutPrincipal.height / 2) {
            Color.GREEN
        } else if (x < layoutPrincipal.width / 2 && y >= layoutPrincipal.height / 2) {
            Color.BLUE
        } else {
            Color.YELLOW
        }
    }
}