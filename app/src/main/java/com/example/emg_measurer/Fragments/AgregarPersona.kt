package com.example.emg_measurer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.example.emg_measurer.R
import com.example.emg_measurer.entities.personas
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class AgregarPersona : Fragment() {

    var db = Firebase.firestore
    lateinit var vAdd: View
    lateinit var buttonOK: Button
    lateinit var nameUser: TextView
    lateinit var apelliUser: TextView
    lateinit var femBox: CheckBox //checkBox2
    lateinit var mascBox: CheckBox

    var genero = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        vAdd = inflater.inflate(R.layout.fragment_agregar_persona, container, false)
        buttonOK = vAdd.findViewById(R.id.buttonOK)
        nameUser = vAdd.findViewById(R.id.editTextTextPersonName2)
        apelliUser = vAdd.findViewById(R.id.editTextTextPersonName)
        femBox = vAdd.findViewById(R.id.checkBox2)
        mascBox = vAdd.findViewById(R.id.checkBox3)
        return vAdd
    }

    override fun onStart() {
        super.onStart()

        buttonOK.setOnClickListener{
            if ( nameUser.text.isNotEmpty() && apelliUser.text.isNotEmpty()){
                if(femBox.isChecked)
                    genero = true
                else if(mascBox.isChecked)
                    genero = false

                db.collection("Users").add(personas(nameUser.text.toString(),apelliUser.text.toString(),genero))

             }
            }
        }
    }