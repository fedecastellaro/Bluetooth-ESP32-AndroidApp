package com.example.emg_measurer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.emg_measurer.R

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult


class Register : Fragment() {

    lateinit var vReg: View
    lateinit var RegisterButton: Button
    lateinit var LogButton: Button
    lateinit var campoName: TextView
    lateinit var campoMail: TextView
    lateinit var campoPassword: TextView
    lateinit var registerRootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vReg = inflater.inflate(R.layout.fragment_register, container, false)

        campoMail = vReg.findViewById(R.id.campoMail)
        campoName = vReg.findViewById(R.id.campoNombre)
        campoPassword = vReg.findViewById(R.id.campoPassword)

        registerRootLayout = vReg.findViewById(R.id.frameLayoutLogin)

        RegisterButton = vReg.findViewById(R.id.buttonReg2)
        LogButton = vReg.findViewById(R.id.btnLogin2)
        return vReg
    }

    override fun onStart() {
        super.onStart()

        LogButton.setOnClickListener {
            val action = RegisterDirections.actionRegister4ToLogin()
            vReg.findNavController().navigate(action)
        }
        RegisterButton.setOnClickListener {
            val password: String = campoPassword.text.toString()
            val mail: String = campoMail.text.toString()

            if (password.isNotEmpty() && mail.isNotEmpty())
            {
                if ( password.length <= 6)
                {
                    Snackbar.make(
                        registerRootLayout,
                        "La contraseña debe contener un minimo de 6 caracteres.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else
                {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val firebaseUser: FirebaseUser = it.result!!.user!!
                            Snackbar.make(
                                registerRootLayout,
                                "Usuario registrado con éxito!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        else {
                            Snackbar.make(
                                registerRootLayout,
                                "Error al registrar Usuario",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                }

            else {
                if (mail.isEmpty() && password.isEmpty()) {
                    Snackbar.make(
                        registerRootLayout,
                        "Ingrese usuario y contraseña",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                if (password.isNotEmpty() && mail.isEmpty()) {
                    Snackbar.make(registerRootLayout, "Campo de mail vacío", Snackbar.LENGTH_SHORT)
                        .show()
                }
                if (mail.isNotEmpty() && password.isEmpty()) {
                    Snackbar.make(
                        registerRootLayout,
                        "Campo de password vacío",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}