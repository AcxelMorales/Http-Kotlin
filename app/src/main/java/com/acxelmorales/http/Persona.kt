package com.acxelmorales.http

data class Persona (val nombre : String, val pais : String, val estado : String, val exp : Int) {

    override fun toString(): String {
        return "Nombre: $nombre, País: $pais, Estado: $estado, Experiencia: $exp"
    }

}
