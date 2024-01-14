package Entidades

import java.util.*
import java.io.Serializable



// entidad generovideojuego
data class GeneroVideojuego (
    val nombregenero: String,
    val descripgenero: String,
    var cantidad: Int,
    val restriccionEdad: Boolean,
    val fechaingreso: Date

): Serializable{
    override fun toString(): String {
        return "Genero: $nombregenero\n" +
                "Descripcion: $descripgenero\n" +
                "FechaCreacion: $fechaingreso\n" +
                "CantidadDisponible: $cantidad\n" +
                "MayorEdad: $restriccionEdad\n"
    }
}