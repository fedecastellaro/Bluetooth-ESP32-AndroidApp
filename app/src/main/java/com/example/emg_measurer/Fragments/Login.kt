package com.example.emg_measurer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.emg_measurer.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class Login : Fragment() {

    lateinit var vLogin: View
    lateinit var btnlogin: Button
    lateinit var btnRegister: Button
    lateinit var userEmail: TextView
    lateinit var userPass: TextView
    lateinit var loginRootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vLogin = inflater.inflate(R.layout.fragment_login, container, false)

        btnRegister = vLogin.findViewById(R.id.btnRegLogin)
        btnlogin = vLogin.findViewById(R.id.buttonLogin)
        userEmail = vLogin.findViewById(R.id.userMailInput)
        userPass = vLogin.findViewById(R.id.userPassInput)
        loginRootLayout = vLogin.findViewById(R.id.frameLayoutLogin)


        return vLogin
    }

    override fun onStart() {
        super.onStart()
        btnRegister.setOnClickListener {
            val action2 = LoginDirections.actionLoginToRegister4()
            vLogin.findNavController().navigate(action2)
        }

        btnlogin.setOnClickListener {
            val mail = userEmail.text.toString()
            val pass = userPass.text.toString()

            if (pass.isNotEmpty() && mail.isNotEmpty())
            {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,pass).addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        Snackbar.make(loginRootLayout, "Bienvenido!", Snackbar.LENGTH_SHORT).show()
                        val action = LoginDirections.actionLoginToMainActivity2()
                        vLogin.findNavController().navigate(action)
                    }
                    else{
                        Snackbar.make(loginRootLayout, "Usuario no encontrado!", Snackbar.LENGTH_SHORT).show()

                    }
                }
            }
            if (mail.isEmpty() && pass.isEmpty())
            {
                Snackbar.make(loginRootLayout, "Ingrese usuario y contraseña", Snackbar.LENGTH_SHORT).show()
            }
            if (pass.isNotEmpty() && mail.isEmpty())
            {
                Snackbar.make(loginRootLayout, "Campo de mail vacío", Snackbar.LENGTH_SHORT).show()
            }
            if (mail.isNotEmpty() && pass.isEmpty())
            {
                Snackbar.make(loginRootLayout, "Campo de password vacío", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}