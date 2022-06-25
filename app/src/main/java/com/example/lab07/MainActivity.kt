package com.example.lab07

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManger: SensorManager
    private lateinit var texto: TextView
    private lateinit var mensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        texto=findViewById(R.id.coordenadas)
        mensaje=findViewById(R.id.mensaje)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff(){
        sensorManger=getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{
            sensorManger.registerListener(this,it,SensorManager.SENSOR_DELAY_FASTEST,SensorManager.SENSOR_DELAY_FASTEST)

        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type==Sensor.TYPE_ACCELEROMETER){
            val x=event.values[0]
            val y=event.values[1]
            val z=event.values[2]

            texto.text="x=${x.toInt()}\ny=${y.toInt()}\nz=${z.toInt()}"
            if(x.toInt()==9){
                mensaje.text="Horizontal 2"
            }
            if(x.toInt()==-9){
                mensaje.text="Horizontal 1"
            }
            if(y.toInt()==9){
                mensaje.text="Vertical 1"
            }
            if(y.toInt()==-9){
                mensaje.text="Vertical 2"
            }
            if(x.toInt() in 1..8 || x.toInt() in -1 downTo -8 || y.toInt() in 1..8 || y.toInt() in -1 downTo -8){
                mensaje.text="Girando"
            }
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}