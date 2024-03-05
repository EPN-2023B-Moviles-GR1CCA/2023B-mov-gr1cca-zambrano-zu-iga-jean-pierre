package com.example.examen_bi_jean_zambrano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class GeneroListView : AppCompatActivity() {

   val arreglo = TiendaBaseDatos.TiendaVideojuego!!.obtenerTodosDatosGenero()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genero_list_view)

        val botonListView = findViewById<Button>(
            R.id.btn_agregar
        )

        botonListView.setOnClickListener {
            irActividad(CrudGenero::class.java)
        }

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador

        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Obtén la posición del elemento seleccionado en el ListView
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Obtén el genero seleccionado
        val generoSeleccionado = arreglo[posicion]

        return when (item.itemId) {
            R.id.mi_editar -> {
                val intent = Intent(this, Editar_Genero::class.java)

                // Pasa los atributos a la actividad EditGenero
                intent.putExtra("id", generoSeleccionado.id)
                intent.putExtra("nombre", generoSeleccionado.nombre)
                intent.putExtra("descripcion", generoSeleccionado.descripcion)
                intent.putExtra("cantidad", generoSeleccionado.cantidad)
                intent.putExtra("restriccionEdad", generoSeleccionado.restriccionEdad)
                intent.putExtra("fechaIngreso", generoSeleccionado.fechaingreso)

                startActivity(intent)
                return true
            }

            R.id.mi_eliminar -> {
                val respuesta = TiendaBaseDatos
                    .TiendaVideojuego!!
                    .eliminarGenero(
                        generoSeleccionado.id
                    )
                if (respuesta) mostrarSnackbar("Género Eliminado")
                eliminarGN(generoSeleccionado.id.toString())
                irActividad(GeneroListView::class.java)
                return true
            }
            R.id.mi_ver_videojuego ->{
                val intent = Intent(this, VideoListView::class.java)

                intent.putExtra("id", generoSeleccionado.id)
                intent.putExtra("nombre", generoSeleccionado.nombre)

                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)

        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_generolist), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    fun eliminarGN(id:String){
        val db = Firebase.firestore
        val referenciaGN = db.collection("generos");
        referenciaGN.document(id).collection("videojuegos").get()
            .addOnSuccessListener {
                // it => eso (lo que llegue)
                for (videojuegos in it) {
                    referenciaGN.document(id).collection("videojuegos").document(videojuegos.id)
                        .delete() // elimina
                        .addOnCompleteListener { /* Si todo salio bien */ }
                        .addOnFailureListener { /* Si algo salio mal*/ }
                }
            }

        referenciaGN
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { /* Si todo salio bien*/ }
            .addOnFailureListener { /* Si algo salio mal*/ }
    }

}