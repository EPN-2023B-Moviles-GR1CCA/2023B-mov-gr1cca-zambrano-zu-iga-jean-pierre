package com.example.examen_bi_jean_zambrano

import java.util.Date

class BDVideojuegos (
    var id: Int,
    var nombreJ: String,
    var disponible: Boolean,
    var plataforma: String,
    var puntaje: Int,
    var precio: Double,
    var lanzamiento: Date,
    var generoId: Int

) {
    override fun toString(): String {
        return "BDVideojuegos(id=$id, nombreJ='$nombreJ', disponible=$disponible, plataforma='$plataforma', puntaje=$puntaje, precio=$precio, lanzamiento=$lanzamiento, generoId=$generoId)"
    }
}