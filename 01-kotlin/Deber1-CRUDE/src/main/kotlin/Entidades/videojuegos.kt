package Entidades

import java.io.Serializable
import java.util.*

// Entidad videokuego
data class videojuegos(
    val nombre: String,
    val disponible: Boolean,
    val plataforma: String,
    val puntaje: Int,
    val precio: Double,
    val lanzamiento: Date

    // Otras variables de tipo Booleano, String pueden ser agregadas de manera similar
) : Serializable{
    override fun toString(): String {
        return "\nNombre:  $nombre\n" +
                "Diponible: $disponible\n" +
                "Plataforma: $plataforma\n" +
                "Fecha de lanzamiento: $lanzamiento\n" +
                "Calificacion: $puntaje\n" +
                "Precio:$precio\n"
    }
}