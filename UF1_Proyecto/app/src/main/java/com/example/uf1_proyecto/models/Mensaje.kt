package com.example.uf1_proyecto.models

class Mensaje {
    var mensaje: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(mensaje: String?, senderId: String?){
        this.mensaje = mensaje
        this.senderId = senderId
    }
}