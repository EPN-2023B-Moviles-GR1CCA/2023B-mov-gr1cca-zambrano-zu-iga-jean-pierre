package com.example.examen_bi_jean_zambrano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CrudVideojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_videojuego)

        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombreJ = findViewById<EditText>(R.id.input_nombre)
            val disponible = findViewById<EditText>(R.id.input_disponible)
            val plataforma = findViewById<EditText>(R.id.input_plataforma)
            val puntaje = findViewById<EditText>(R.id.input_puntaje)
            val precio = findViewById<EditText>(R.id.input_precio)
            val lanzamiento = findViewById<EditText>(R.id.input_lanzamiento)
            val generoId = findViewById<EditText>(R.id.input_genero_id)

            val videojuego = TiendaBaseDatos.TiendaVideojuego!!
                .consultarVideojuegoPorID(
                    id.text.toString().toInt()
                )

            if (videojuego.id == 0) {
                mostrarSnackbar("Videojuego no encontrado")
            }

            id.setText(videojuego.id.toString())
            nombreJ.setText(videojuego.nombreJ)
            disponible.setText(videojuego.disponible.toString())
            plataforma.setText(videojuego.plataforma)
            puntaje.setText(videojuego.puntaje.toString())
            precio.setText(videojuego.precio.toString())
            lanzamiento.setText(videojuego.lanzamiento.toString())
            generoId.setText(videojuego.generoId.toString())

            mostrarSnackbar("Videojuego encontrado")
        }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombreJ = findViewById<EditText>(R.id.input_nombre)
            val disponible = findViewById<EditText>(R.id.input_disponible)
            val plataforma = findViewById<EditText>(R.id.input_plataforma)
            val puntaje = findViewById<EditText>(R.id.input_puntaje)
            val precio = findViewById<EditText>(R.id.input_precio)
            val lanzamiento = findViewById<EditText>(R.id.input_lanzamiento)
            val generoId = findViewById<EditText>(R.id.input_genero_id)

            val respuesta = TiendaBaseDatos
                .TiendaVideojuego!!.crearVideojuego(
                    nombreJ.text.toString(),
                    disponible.text.toString().toBoolean(),
                    plataforma.text.toString(),
                    puntaje.text.toString().toInt(),
                    precio.text.toString().toDouble(),
                    lanzamiento.text.toString(),
                    generoId.text.toString().toInt()
                )

            if (respuesta) mostrarSnackbar("Videojuego creado")
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombreJ = findViewById<EditText>(R.id.input_nombre)
            val disponible = findViewById<EditText>(R.id.input_disponible)
            val plataforma = findViewById<EditText>(R.id.input_plataforma)
            val puntaje = findViewById<EditText>(R.id.input_puntaje)
            val precio = findViewById<EditText>(R.id.input_precio)
            val lanzamiento = findViewById<EditText>(R.id.input_lanzamiento)
            val generoId = findViewById<EditText>(R.id.input_genero_id)

            val respuesta = TiendaBaseDatos.TiendaVideojuego!!.actualizarVideojuegoFormulario(
                nombreJ.text.toString(),
                disponible.text.toString().toBoolean(),
                plataforma.text.toString(),
                puntaje.text.toString().toInt(),
                precio.text.toString().toDouble(),
                lanzamiento.text.toString(),
                generoId.text.toString().toInt(),
                id.text.toString().toInt()
            )

            if (respuesta) mostrarSnackbar("Videojuego actualizado")
        }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = TiendaBaseDatos
                .TiendaVideojuego!!
                .eliminarVideojuegoFormulario(
                    id.text.toString().toInt()
                )

            if (respuesta) mostrarSnackbar("Videojuego eliminado")
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_sqlite),
                texto,
                Snackbar.LENGTH_LONG
            )
            .show()
    }
}