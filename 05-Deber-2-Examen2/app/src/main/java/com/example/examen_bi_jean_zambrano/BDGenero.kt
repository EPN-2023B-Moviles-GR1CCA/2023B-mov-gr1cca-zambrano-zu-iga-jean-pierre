package com.example.examen_bi_jean_zambrano

import java.util.Date

class BDGenero (
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var cantidad: Int,
    var restriccionEdad: Boolean,
    var fechaingreso: Date,
) {
    override fun toString(): String {
        return "$nombre, $descripcion"
    }
}