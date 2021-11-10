package com.example.emg_measurer.adapters

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.emg_measurer.Fragments.Principal
import com.example.emg_measurer.R
import com.example.emg_measurer.entities.personas


class adapterUsuarios(
    private var usuariosList: MutableList<personas>,
    var clicklistenerfunciones: Principal,
): RecyclerView.Adapter<adapterUsuarios.PersonasHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasHolder
    {
        //tengo que ir a buscar el xml de los items de la tarea
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.items_personas,parent,false)
        return (adapterUsuarios.PersonasHolder(view))
    }

    override fun getItemCount(): Int {
        return usuariosList.size
    }

    override fun onBindViewHolder(holder: PersonasHolder, position: Int)
    {
        holder.setName(usuariosList[position].apellido+ " " + usuariosList[position].name)
        holder.setImage(usuariosList[position].genero)
//        holder.setName(usuariosList[position].age.toString())

        holder.getCardLayout().setOnClickListener {
            //esta es la funcion que le mando desde el fragment
            clicklistenerfunciones.onItemClick(position)
        }

    }

    class PersonasHolder(v: View): RecyclerView.ViewHolder(v)
    {
        // Acá tengo que setear todas las caracteristicas de c/item de mi lista ( imagen, texto, botones, etc)
        private var view: View
        init{
            this.view = v
        }
        fun setName(name: String) {
            val txt: TextView = view.findViewById(R.id.txt_name_item)
            txt.text = name
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_item)
        }

        fun setImage(genero: Boolean) {
            val img: ImageView = view.findViewById(R.id.img_item)
            if(genero) // feminino
                img.setImageResource(R.drawable.fem_user2)
            else
                img.setImageResource(R.drawable.user)

        }
        fun getTextLayout(): TextView {
            return view.findViewById(R.id.txt_name_item)
        }
    }

}