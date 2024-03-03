package com.example.examen_bi_jean_zambrano

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Base de datos sqlite
        TiendaBaseDatos.TiendaVideojuego = ESQHVideojuego(this)

        // Inicializamo el FireBase
        // db = FirebaseFirestore.getInstance()

        val botonListView = findViewById<Button>(R.id.btn_genero)
        botonListView.setOnClickListener {
            irActividad(GeneroListView::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

