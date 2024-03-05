package com.example.examen_bi_jean_zambrano

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ESQHVideojuego(
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "TiendaVideojuego", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {

        val scriptSQLCrearTablaGenero =
            """
               CREATE TABLE generos(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               descripcion VARCHAR(50),
               cantidad INTEGER,
               restriccionEdad INTEGER,
               fechaingreso VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaGenero)

        val scriptSQLCrearTablaVideo =
            """
               CREATE TABLE videojuegos(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombreJ VARCHAR(50),
               disponible INTEGER,
               plataforma VARCHAR(50),
               puntaje INTEGER,
               precio INTEGER,
               lanzamiento VARCHAR(50),
               generoId INTEGER,
               FOREIGN KEY (generoId) REFERENCES generos(id)
               
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaVideo)

    }
//-----------------------------------------
    fun crearGN(
        nombre: String,
        descripcion: String,
        cantidad: Int,
        restriccionEdad: Int,
        fechaIngreso: String
    ):  Pair<Boolean, Long> {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("cantidad", cantidad)
            put("restriccionEdad", restriccionEdad) // Convierte a Int
            put("fechaingreso", fechaIngreso)
        }

        val resultadoGuardar = basedatosEscritura
            .insert(
                "generos", // Nombre de la tabla "generos"
                null,
                valoresAGuardar
            )

        basedatosEscritura.close()

    val respuesta = resultadoGuardar.toInt() != -1
    val idDatoInsertado = if (respuesta) resultadoGuardar else -1L // Obtiene el ID del último dato insertado

    return Pair(respuesta, idDatoInsertado)
    }
    //------------------------------------
    fun crearVideojuego(
        nombreJ: String,
        disponible: Int,
        plataforma: String,
        puntaje: Int,
        precio: Int,
        lanzamiento: String,
        generoId: Int
    ): Pair<Boolean, Long> {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombreJ", nombreJ)
            put("disponible", disponible) // Convierte a Int
            put("plataforma", plataforma)
            put("puntaje", puntaje)
            put("precio", precio)
            put("lanzamiento", lanzamiento)
            put("generoId", generoId)
        }

        val resultadoGuardar = basedatosEscritura
            .insert(
                "videojuegos", // Nombre de la tabla "videojuegos"
                null,
                valoresAGuardar
            )

        basedatosEscritura.close()

        val exito = resultadoGuardar.toInt() != -1
        val idDatoInsertado = if (exito) resultadoGuardar else -1L // Obtiene el ID del último dato insertado
        return Pair(exito, idDatoInsertado)
    }

    //------------------------------------------------
///-------------------------------------------

    fun actualizarVideojuego(
        nombreJ: String,
        disponible: Int,
        plataforma: String,
        puntaje: Int,
        precio: Int,
        lanzamiento: String,
        generoId: Int,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombreJ", nombreJ)
            put("disponible", disponible) // Convertir a Int
            put("plataforma", plataforma)
            put("puntaje", puntaje)
            put("precio", precio)
            put("lanzamiento", lanzamiento)
            put("generoId", generoId)
        }

        // WHERE id = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())

        val resultadoActualizacion = conexionEscritura.update(
            "videojuegos", // Nombre de la tabla
            valoresAActualizar, // Valores a actualizar
            "id=?", // Condición WHERE
            parametrosConsultaActualizar // Parámetros para la condición WHERE
        )

        conexionEscritura.close()

        // Devolver true si la actualización fue exitosa, false de lo contrario
        return resultadoActualizacion != -1
    }

    ///----------------------------------------------------------------------------
    fun actualizarGenero(
        nombre: String,
        descripcion: String,
        cantidad: Int,
        restriccionEdad: Int,
        fechaIngreso: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("cantidad", cantidad)
            put("restriccionEdad", restriccionEdad) // Convertir a Int
            put("fechaingreso", fechaIngreso)
        }

        // WHERE id = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())

        val resultadoActualizacion = conexionEscritura.update(
            "generos", // Nombre de la tabla
            valoresAActualizar, // Valores a actualizar
            "id=?", // Condición WHERE
            parametrosConsultaActualizar // Parámetros para la condición WHERE
        )

        conexionEscritura.close()

        // Devolver true si la actualización fue exitosa, false de lo contrario
        return resultadoActualizacion != -1
    }
    //--------------------------------------------------------

    fun eliminarGenero(id: Int): Boolean {
        val conexionEscritura = writableDatabase

        // WHERE id = ?
        val parametrosConsultaDelete = arrayOf(id.toString())

        // Eliminar el registro
        val resultadoEliminacion = conexionEscritura.delete(
            "generos", // Nombre de la tabla
            "id=?", // Condición WHERE
            parametrosConsultaDelete // Parámetros para la condición WHERE
        )
        conexionEscritura.close()
        // Devolver true si la eliminación fue exitosa, false de lo contrario
        return resultadoEliminacion != -1
    }
    //---------------------------------------------------------------------

    fun eliminarVideojuego(id: Int): Boolean {
        val conexionEscritura = writableDatabase

        // WHERE generoId = ?
        val parametrosConsultaDelete = arrayOf(id.toString())

        // Eliminar los registros de la tabla videojuegos que hacen referencia al género a eliminar
        val resultadoEliminacionVideojuegos = conexionEscritura.delete(
            "videojuegos", // Nombre de la tabla
            "id=?", // Condición WHERE
            parametrosConsultaDelete // Parámetros para la condición WHERE
        )
        conexionEscritura.close()
        // Devolver true si al menos un registro fue eliminado, false de lo contrario
        return resultadoEliminacionVideojuegos != -1
    }

    //------------------------------------------------///
    fun obtenerTodosDatosGenero(): ArrayList<BDGenero> {
        val generosList = ArrayList<BDGenero>()
        val baseDatosLectura = readableDatabase

        val scriptConsulta = "SELECT * FROM generos"
        val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val id = resultadoConsulta.getInt(0)
                val nombre = resultadoConsulta.getString(1)
                val descripcion = resultadoConsulta.getString(2)
                val cantidad = resultadoConsulta.getInt(3)
                val restriccionEdad = resultadoConsulta.getInt(4) == 1
                val fechaIngresoString = resultadoConsulta.getString(5)
                val fechaIngreso = SimpleDateFormat("yyyy-MM-dd").parse(fechaIngresoString)

                val genero = BDGenero(id, nombre, descripcion, cantidad, restriccionEdad, fechaIngreso)
                generosList.add(genero)

            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        baseDatosLectura.close()

        return generosList
    }
////-----------------------------------------------------------

    fun obtenerVideojuegosPorGenero(generoId: Int): ArrayList<BDVideojuegos> {
        val videojuegos = ArrayList<BDVideojuegos>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = "SELECT * FROM videojuegos WHERE generoId = ?"
        val parametrosConsultaLectura = arrayOf(generoId.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombreJ = resultadoConsultaLectura.getString(1)
                val disponible = resultadoConsultaLectura.getInt(2) == 1
                val plataforma = resultadoConsultaLectura.getString(3)
                val puntaje = resultadoConsultaLectura.getInt(4)
                val precio = resultadoConsultaLectura.getDouble(5)
                val lanzamientoString = resultadoConsultaLectura.getString(6)
                val generoId = resultadoConsultaLectura.getInt(7)

                // Convertir la fecha de lanzamiento de String a Date
                val lanzamiento = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(lanzamientoString)

                val videojuego = BDVideojuegos(id, nombreJ, disponible, plataforma, puntaje, precio, lanzamiento, generoId)
                videojuegos.add(videojuego)

            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return videojuegos
    }


    //--------------------------------------------



    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int, p2: Int) {}

}