package com.example.examen_bi_jean_zambrano

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Base de datos sqlite
        TiendaBaseDatos.TiendaVideojuego = ESQHVideojuego(this)

        val botonListView = findViewById<Button>(R.id.btn_genero)
        botonListView.setOnClickListener {
            irActividad(VideoListView::class.java)
        }
    }




    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

