package com.example.examen_bi_jean_zambrano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrudVideojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_videojuego)

        val generoId = intent.getIntExtra("id", 0)
        val nombreGenero  = intent.getStringExtra("nombre")

        val botonCrearVideojuego = findViewById<Button>(R.id.btn_agregar2)

        botonCrearVideojuego.setOnClickListener {
            val nombreJ = findViewById<EditText>(R.id.input_nombre_videojuego)

            val plataforma = findViewById<EditText>(R.id.input_plataforma)
            val puntaje = findViewById<EditText>(R.id.input_puntaje)
            val precio = findViewById<EditText>(R.id.input_precio)
            val lanzamiento = findViewById<EditText>(R.id.input_fecha)

            val disponible = findViewById<EditText>(R.id.input_disponible)
            val disponibleBoleano = if (disponible.text.toString().equals("Si", ignoreCase = true)) 1 else 0


            val (respuesta, idVideojuego) = TiendaBaseDatos
                .TiendaVideojuego!!.crearVideojuego(
                    nombreJ.text.toString(),
                    disponibleBoleano,
                    plataforma.text.toString(),
                    puntaje.text.toString().toInt(),
                    precio.text.toString().toInt(),
                    lanzamiento.text.toString(),
                    generoId
            )

            if (respuesta) {

                crearVideojuego(

                    idVideojuego.toString(),
                    generoId.toString(),
                    nombreJ.text.toString(),
                    disponibleBoleano,
                    plataforma.text.toString(),
                    puntaje.text.toString().toInt(),
                    precio.text.toString().toInt(),
                    lanzamiento.text.toString())
                    val intent = Intent(this, VideoListView::class.java)
                    intent.putExtra("id", generoId)
                    intent.putExtra("nombre", nombreGenero)
                    startActivity(intent)
            }
        }

    }

    fun crearVideojuego(
        id: String,
        generoId: String,
        nombre: String,
        disponibleBoleano: Int,
        plataforma: String,
        puntaje: Int,
        precio: Int,
        lanzamiento: String
        ){
            val db = Firebase.firestore
            val referenciaAVideojuegos = db.collection("generos").document(generoId)
                .collection("videojuegos")

            val datosVideojuegos = hashMapOf(
                "nombre" to nombre,
                "disponibleBoleano" to disponibleBoleano,
                "plataforma" to plataforma,
                "puntaje" to puntaje,
                "precio" to precio,
                "lanzamiento" to lanzamiento

            )

            // identificador quemado (crear/actualizar)
            referenciaAVideojuegos
                .document(id)
                .set(datosVideojuegos)
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
        }

    }

