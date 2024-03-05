package com.example.examen_bi_jean_zambrano

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class CrudGenero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_genero)

        val botonCrearGenero = findViewById<Button>(R.id.btn_agregar_genero)
        botonCrearGenero.setOnClickListener{crearEjemplo()}

        botonCrearGenero.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_genero)
            val descripcion = findViewById<EditText>(R.id.input_descripcion_genero)
            val cantidad = findViewById<EditText>(R.id.input_cantidad_genero)
            val fechaIngreso = findViewById<EditText>(R.id.input_fecha_ingreso_genero)

            val restriccionEdad = findViewById<EditText>(R.id.input_restriccion_edad_genero)

            val restriccionEdadBoolean = if (restriccionEdad.text.toString().equals("si", ignoreCase = true)) 1 else 0

            val respuesta = TiendaBaseDatos
                .TiendaVideojuego!!.crearGenero(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    cantidad.text.toString().toInt(),
                    restriccionEdadBoolean,
                    fechaIngreso.text.toString()
                )

            if (respuesta) mostrarSnackbar("Género Creado")
            irActividad(GeneroListView::class.java)
        }
    }
    fun crearEjemplo() {
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db
            .collection("ejemplo")
        // .document("id_hijo")
        // .collection("estudiante")
        val datosEstudiante = hashMapOf(
            "nombre" to "Adrian",
            "graduado" to false,
            "promedio" to 14.00,
            "direccion" to hashMapOf(
                "direccion" to "Mitad del mundo",
                "numeroCalle" to 1234
            ),
            "materias" to listOf("web", "moviles")
        )
        // identificador quemado (crear/actualizar)
        referenciaEjemploEstudiante
            .document("12345678")
            .set(datosEstudiante)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        // identificador quemado pero autogenerado con Date().time
        val identificador = Date().time
        referenciaEjemploEstudiante // (crear/actualizar)
            .document(identificador.toString())
            .set(datosEstudiante)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        // Sin IDENTIFICADOR (crear)
        referenciaEjemploEstudiante
            .add(datosEstudiante)
            .addOnCompleteListener { }
            .addOnFailureListener { }
    }



        fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_crudgenero), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }




    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}