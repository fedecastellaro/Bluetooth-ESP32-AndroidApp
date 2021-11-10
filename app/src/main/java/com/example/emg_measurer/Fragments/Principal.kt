package com.example.emg_measurer.Fragments

import android.app.ActionBar
import android.app.Notification
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emg_measurer.R
import com.example.emg_measurer.adapters.adapterUsuarios
import com.example.emg_measurer.entities.personas
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.security.Principal


class Principal : Fragment() {
    lateinit var vPrincipal: View
    lateinit var bottonAdd: Button

    var allPersonas : MutableList<personas> = arrayListOf()

    lateinit var recycleUsuarios : RecyclerView
    private lateinit var usuariosListAdapter: adapterUsuarios
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var PrincipalRootLayout: ConstraintLayout

    var db = Firebase.firestore

    companion object{
        var USUARIO = ""
    }
    interface  listenerclicks{
        fun onItemClick(pos: Int)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
//            activity?.actionBar?.title = "Elija Usuario"
            super.onCreate(savedInstanceState)
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment

            vPrincipal =  inflater.inflate(R.layout.fragment_principal, container, false)
            recycleUsuarios = vPrincipal.findViewById(R.id.rec_tareas)
            PrincipalRootLayout = vPrincipal.findViewById(R.id.frameLayout3)

            bottonAdd = vPrincipal.findViewById(R.id.buttonAdd)

            usuariosListAdapter = adapterUsuarios(allPersonas, this)

            return vPrincipal
        }

    override fun onStart() {
        super.onStart()

        recycleUsuarios.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycleUsuarios.layoutManager = linearLayoutManager
        recycleUsuarios.adapter = usuariosListAdapter

        EventChangeListener()

        bottonAdd.setOnClickListener {
            val action = PrincipalDirections.actionPrincipalToAgregarPersona()
            vPrincipal.findNavController().navigate(action)
        }
    }


    fun onItemClick ( position : Int ) {

        USUARIO = allPersonas[position].apellido + " " + allPersonas[position].name

//        val action3 = PrincipalDirections.actionPrincipalToBTFragment(text2Send)
            val actiontest = PrincipalDirections.actionPrincipalToBTActivity()
            vPrincipal.findNavController().navigate(actiontest)
    }


    fun EventChangeListener()
    {
        db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .orderBy("apellido")
            .addSnapshotListener(object :EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null)
                {
                    //Error
                    Snackbar.make(PrincipalRootLayout, "" + error, Snackbar.LENGTH_SHORT).show()
                    return
                }
                allPersonas.clear()
                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED)
                    {
                        allPersonas.add(dc.document.toObject(personas::class.java))
                    }
                }
                usuariosListAdapter.notifyDataSetChanged()
            }
        })
    }
}