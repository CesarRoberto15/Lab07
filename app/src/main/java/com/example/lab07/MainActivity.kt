package com.example.lab07

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManagerAcelerometro: SensorManager
    private lateinit var sensorManagerMagnetometro: SensorManager
    private lateinit var textoAcelerometro: TextView
    private lateinit var mensajeSensores: TextView
    private lateinit var textoMagnetometro: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        textoAcelerometro = findViewById(R.id.coordenadasAcelerometro)
        mensajeSensores = findViewById(R.id.mensajeSensores)
        textoMagnetometro = findViewById(R.id.coordenadasMagnetometro)
        
        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        sensorManagerAcelerometro = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManagerAcelerometro.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManagerAcelerometro.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        sensorManagerMagnetometro = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManagerMagnetometro.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
            sensorManagerMagnetometro.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }

        if (null == sensorManagerMagnetometro)
            finish();

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            textoAcelerometro.text =
                "(ACELERÓMETRO)\nX = ${x.toInt()}\nY = ${y.toInt()}\nZ = ${z.toInt()}"
            if (x.toInt() == 9) {
                mensajeSensores.text = "Horizontal 2"
            }
            if (x.toInt() == -9) {
                mensajeSensores.text = "Horizontal 1"
            }
            if (y.toInt() == 9) {
                mensajeSensores.text = "Vertical 1"
            }
            if (y.toInt() == -9) {
                mensajeSensores.text = "Vertical 2"
            }
            if (x.toInt() in 1..8 || x.toInt() in -1 downTo -8 || y.toInt() in 1..8 || y.toInt() in -1 downTo -8) {
                mensajeSensores.text = "Girando"
            }
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val magX = event.values[0]
            val magY = event.values[1]
            val magZ = event.values[2]

            val df = DecimalFormat("#.###")
            df.roundingMode = RoundingMode.DOWN
            val magnitude =
                df.format(Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ).toDouble()))

            textoMagnetometro.text =
                "(MAGNETÓMETRO)\nX = ${magX.toInt()}\nY = ${magY.toInt()}\nZ = ${magZ.toInt()}\nValor = ${magnitude}"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}
