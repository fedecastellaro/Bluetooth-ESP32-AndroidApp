package com.example.emg_measurer.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.emg_measurer.R
import ingenieria.jhr.bluetoothjhr.BluetoothJhr
import org.jetbrains.anko.toast

class BTActivity : AppCompatActivity() {

    lateinit var list_dispo : ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_btactivity)
        list_dispo = findViewById(R.id.list_display)

        val blue = BluetoothJhr(this,list_dispo, MainActivity3::class.java)
        //para listView normal solo accedemos a onBluetooth
        blue.onBluetooth()

        list_dispo.setOnItemClickListener { _, _, i, l ->
            blue.bluetoothSeleccion(i)
        }
    }

}



