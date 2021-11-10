package com.example.emg_measurer.entities

//False -> Masculino
//True  -> Femenino

class personas(name: String,apellido:String, genero:Boolean) {
    var name: String = ""
    var apellido: String =""
    var genero: Boolean = true
//    var age: Int = 0

    constructor() : this("","",true)

    init{
        this.name = name!!
        this.apellido = apellido!!
        this.genero = genero!!
    }
}