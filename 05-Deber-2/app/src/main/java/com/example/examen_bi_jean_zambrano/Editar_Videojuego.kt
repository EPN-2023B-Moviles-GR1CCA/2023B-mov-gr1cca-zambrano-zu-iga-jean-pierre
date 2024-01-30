package com.example.examen_bi_jean_zambrano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Date

class Editar_Videojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_videojuego)

        val nombreGenero = intent.getStringExtra("nombreGenero") ?: ""
        val id = intent.getIntExtra("id", 0)
        val nombreJ = intent.getStringExtra("nombreJ") ?: ""
        val disponible = intent.getIntExtra("disponible", 0)
        val plataforma = intent.getStringExtra("plataforma") ?: ""
        val puntaje = intent.getIntExtra("puntaje", 0)
        val precio = intent.getDoubleExtra("precio", 0.0)
        val generoId = intent.getIntExtra("generoId", 0)


        val lanzamientoDate = intent.getSerializableExtra("lanzamiento") as Date

        // Convierte la fecha a un formato de String
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
        val lanzamientoString = formatoFecha.format(lanzamientoDate)

        // Llena los EditText con la información obtenida
        val editTextNombre = findViewById<EditText>(R.id.input_nombre_videojuego2)
        editTextNombre.setText(nombreJ)

        val editTextDisponible = findViewById<EditText>(R.id.input_disponible2)
        editTextDisponible.setText(disponible.toString())

        val editTextPlataforma = findViewById<EditText>(R.id.input_plataforma2)
        editTextPlataforma.setText(plataforma)

        val editTextPuntaje = findViewById<EditText>(R.id.input_puntaje2)
        editTextPuntaje.setText(puntaje.toString())

        val editTextPrecio = findViewById<EditText>(R.id.input_precio2)
        editTextPrecio.setText(precio.toString())

        val editTextLanzamiento = findViewById<EditText>(R.id.input_fecha2)
        editTextLanzamiento.setText(lanzamientoString)

        val botonActualizarBDD = findViewById<Button>(R.id.btn_editar_videojuego)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_videojuego2)
            val disponible = findViewById<EditText>(R.id.input_disponible2)
            val plataforma = findViewById<EditText>(R.id.input_plataforma2)
            val puntaje = findViewById<EditText>(R.id.input_puntaje2)
            val precio = findViewById<EditText>(R.id.input_precio2)
            val lanzamiento = findViewById<EditText>(R.id.input_fecha2)

            // Obtén el ID y otros atributos de la Intent
            val idVideojuego = intent.getIntExtra("id", 0)

            // Convierte el texto de disponible, puntaje y precio a valores correspondientes
            val disponibleInt = disponible.text.toString().toIntOrNull() ?: 0
            val puntajeInt = puntaje.text.toString().toIntOrNull() ?: 0
            val precioInt = precio.text.toString().toIntOrNull() ?: 0

            // Actualiza el Videojuego en la base de datos
            val respuesta = TiendaBaseDatos.TiendaVideojuego!!.actualizarVideojuego(
                nombre.text.toString(),
                disponibleInt,
                plataforma.text.toString(),
                puntajeInt,
                precioInt,
                lanzamiento.text.toString(), // Asegúrate de manejar correctamente la fecha
                generoId,
                idVideojuego
            )

            if (respuesta) {
                val intent = Intent(this, VideoListView::class.java)
                intent.putExtra("id", generoId)
                intent.putExtra("nombre", nombreGenero)
                startActivity(intent)
            }
        }
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}