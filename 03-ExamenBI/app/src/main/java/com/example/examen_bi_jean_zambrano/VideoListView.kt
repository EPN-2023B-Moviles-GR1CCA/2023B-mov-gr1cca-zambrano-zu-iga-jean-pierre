package com.example.examen_bi_jean_zambrano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.EditText


class VideoListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list_view)

        // Obtén el ID y otros atributos de la Intent
        val idGenero = intent.getIntExtra("id", 0)
        val nombreGenero = intent.getStringExtra("nombre")

        val TextNombre = findViewById<TextView>(R.id.idVideojuego)
        TextNombre.setText(nombreGenero)
    }
} /// me ecuchas ?