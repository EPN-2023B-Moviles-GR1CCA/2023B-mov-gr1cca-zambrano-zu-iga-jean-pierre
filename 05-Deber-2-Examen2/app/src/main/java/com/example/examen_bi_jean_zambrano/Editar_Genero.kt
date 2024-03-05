package com.example.examen_bi_jean_zambrano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date

class Editar_Genero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_genero)

        // Obtén el ID y otros atributos de la Intent
        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val cantidad = intent.getIntExtra("cantidad", 0)
        val fechaIngresoDate = intent.getSerializableExtra("fechaIngreso") as Date

        val restriccionEdad = intent.getBooleanExtra("restriccionEdad", false)

        val textrestriccionEdad = if (restriccionEdad) "Si" else "No"


        // Convierte la fecha a un formato de String
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd") // Ajusta el formato según tus necesidades
        val fechaIngresoString = formatoFecha.format(fechaIngresoDate)

        // Llena los EditText con la información obtenida
        val editTextNombre = findViewById<EditText>(R.id.input_nombre_genero2)
        editTextNombre.setText(nombre)

        val editTextDescripcion = findViewById<EditText>(R.id.input_descripcion_genero2)
        editTextDescripcion.setText(descripcion)

        val editTextCantidad = findViewById<EditText>(R.id.input_cantidad_genero2)
        editTextCantidad.setText(cantidad.toString())

        val editTextRestriccionEdad = findViewById<EditText>(R.id.input_restriccion_edad_genero2)
        editTextRestriccionEdad.setText (textrestriccionEdad)

        val editTextFechaIngreso = findViewById<EditText>(R.id.input_fecha_ingreso_genero2)
        editTextFechaIngreso.setText(fechaIngresoString)

        val botonActualizarBDD = findViewById<Button>(R.id.btn_editar_genero)
        botonActualizarBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_genero2)
            val descripcion = findViewById<EditText>(R.id.input_descripcion_genero2)
            val cantidad = findViewById<EditText>(R.id.input_cantidad_genero2)
            val restriccionEdad = findViewById<EditText>(R.id.input_restriccion_edad_genero2)
            val fechaIngreso = findViewById<EditText>(R.id.input_fecha_ingreso_genero2)

            // Obtén el ID y otros atributos de la Intent
            val idGenero = intent.getIntExtra("id", 0)

            // Convierte el texto de restriccionEdad a un valor booleano
            val restriccionEdadBoolean = restriccionEdad.text.toString().equals("si", ignoreCase = true)

            // Convierte el valor booleano a un entero (1 si es true, 0 si es false)
            val restriccionEdadInt = if (restriccionEdadBoolean) 1 else 0

            // Actualiza el Género en la base de datos
            val respuesta = TiendaBaseDatos.TiendaVideojuego!!.actualizarGenero(
                nombre.text.toString(),
                descripcion.text.toString(),
                cantidad.text.toString().toInt(),
                restriccionEdadInt,
                fechaIngreso.text.toString(),
                idGenero
            )

            if (respuesta) mostrarSnackbar("Género Actualizado")
            irActividad(GeneroListView::class.java)
        }
    }


    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_editargenero), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}