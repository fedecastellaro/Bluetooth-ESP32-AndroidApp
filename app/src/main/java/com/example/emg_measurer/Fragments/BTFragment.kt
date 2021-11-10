package com.example.emg_measurer.Fragments

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.example.emg_measurer.R
import com.example.emg_measurer.activities.MainActivity2
import com.example.emg_measurer.adapters.adapterUsuarios
import com.google.android.material.snackbar.Snackbar


class BTFragment : Fragment() {

    lateinit var vBT: View
    lateinit var textoUser: TextView
    lateinit var buttonStart: Button
    lateinit var BTRootLayout: ConstraintLayout
    private var bluetooth = false

    var aux : Intent? = null
    lateinit var bt: BluetoothSPP


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        bt = app.akexorcist.bluetotohspp.library.BluetoothSPP(requireContext())

        if (!bt.isBluetoothAvailable)
        {
            Snackbar.make(BTRootLayout, "Bluetooth no disponible", Snackbar.LENGTH_SHORT).show()
        }

        if(!bt.isBluetoothEnabled)
        {
            bt.setupService()
            bt.startService(true)
        }

        val intent2 = Intent(requireContext(), DeviceList::class.java)
        startActivityForResult(intent2, BluetoothState.REQUEST_CONNECT_DEVICE)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.btmenu_main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setBTicon (menuItem: MenuItem)
    {
        val id = if (bluetooth) R.drawable.ic_baseline_bluetooth_connected_24
        else R.drawable.ic_baseline_bluetooth_connected_blue

        menuItem.icon = androidx.core.content.ContextCompat.getDrawable(requireContext(),id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btconnectbutton -> {
//                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//                    startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)

                    val intent2 = Intent(requireContext(), DeviceList::class.java)
                    startActivityForResult(intent2, BluetoothState.REQUEST_CONNECT_DEVICE)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vBT =  inflater.inflate(R.layout.fragment_b_t, container, false)
        buttonStart = vBT.findViewById(R.id.buttonSt)
        BTRootLayout = vBT.findViewById(R.id.frameLayoutBT)

        bt.setBluetoothConnectionListener(object : BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                Snackbar.make(BTRootLayout, "Connectado a "+ name + " " + address, Snackbar.LENGTH_SHORT).show()
            }

            override fun onDeviceDisconnected() {
                Snackbar.make(BTRootLayout, "Conexión perdida", Snackbar.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() {
                Snackbar.make(BTRootLayout, "Conexión imposible", Snackbar.LENGTH_SHORT).show()
            }
        })

        return vBT
    }

    override fun onStart() {
        super.onStart()
        buttonStart.setOnClickListener {

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE)
        {
            if (resultCode == Activity.RESULT_OK){
                bt.connect(data)
            }
        }
        else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
            }
        }
    }
}