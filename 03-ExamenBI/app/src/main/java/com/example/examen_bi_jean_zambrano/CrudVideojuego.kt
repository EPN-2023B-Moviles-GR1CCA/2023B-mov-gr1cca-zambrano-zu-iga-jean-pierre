package com.example.examen_bi_jean_zambrano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CrudVideojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_videojuego)

        val generoId = intent.getIntExtra("generoId", 0)
        val nombreSo = intent.getStringExtra("nombreGenero")

        val botonCrearVideojuego = findViewById<Button>(R.id.btn_agregar2)

        botonCrearVideojuego.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_videojuego)

            val plataforma = findViewById<EditText>(R.id.input_plataforma)
            val puntaje = findViewById<EditText>(R.id.input_puntaje)
            val precio = findViewById<EditText>(R.id.input_precio)
            val lanzamiento = findViewById<EditText>(R.id.input_fecha)

            val disponible = findViewById<EditText>(R.id.input_disponible)

            val disponibleBoleano = if (disponible.text.toString().equals("Si", ignoreCase = true)) 1 else 0


            val respuesta = TiendaBaseDatos.TiendaVideojuego!!.crearVideojuego(
                nombre.text.toString(),
                disponibleBoleano,
                plataforma.text.toString(),
                puntaje.text.toString().toInt(),
                precio.text.toString().toInt(),
                lanzamiento.text.toString(),
                generoId
            )

            if (respuesta) {
                val intent = Intent(this, VideoListView::class.java)
                intent.putExtra("id", generoId)
                intent.putExtra("nombre", nombreSo)
                startActivity(intent)

            }
        }

    }
}