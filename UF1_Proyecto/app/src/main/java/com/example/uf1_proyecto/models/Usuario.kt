package com.example.uf1_proyecto.models

class Usuario {
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    // Constructor vacío requerido por Firebase
    constructor()

    constructor(name: String?, email: String?, uid: String?) {
        this.name = name
        this.email = email
        this.uid = uid
    }
}
