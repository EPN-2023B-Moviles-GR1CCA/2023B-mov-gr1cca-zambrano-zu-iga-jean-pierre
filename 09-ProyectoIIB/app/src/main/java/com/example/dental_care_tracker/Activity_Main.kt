package com.example.dental_care_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Activity_Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontrar el ImageView por su ID
        val imageView: ImageView = findViewById(R.id.imagen_svg)

        // Establecer el recurso SVG como fuente del ImageView
        imageView.setImageResource(R.drawable.nombre_de_tu_archivo_svg)
    }
}