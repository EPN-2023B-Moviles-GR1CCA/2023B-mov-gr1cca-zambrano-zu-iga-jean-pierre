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
import android.widget.TextView
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class VideoListView : AppCompatActivity() {

    lateinit var arreglo: ArrayList<BDVideojuegos>;
    lateinit var nombreGenero: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list_view)

        val generoId = intent.getIntExtra("id", 0)
        nombreGenero = intent.getStringExtra("nombre") ?: ""

        val textNombre = findViewById<TextView>(R.id.idVideojuego)
        textNombre.setText(nombreGenero)

        arreglo = TiendaBaseDatos.TiendaVideojuego!!.obtenerVideojuegosPorGenero(generoId)

        val botonListView = findViewById<Button>(R.id.btn_agregar2)
        botonListView.setOnClickListener {
            val intent = Intent(this, CrudVideojuego::class.java)
            intent.putExtra("id", generoId)
            intent.putExtra("nombre", nombreGenero)
            startActivity(intent)
        }

        val listView = findViewById<ListView>(R.id.lv_list_view_video)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador

        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú contextual
        val inflater = menuInflater
        inflater.inflate(R.menu.menuvideo, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Obtén la posición del elemento seleccionado en el ListView
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Obtén el videojuego seleccionado
        val videojuegoSeleccionado = arreglo[posicion]

        return when (item.itemId) {
            R.id.mi_editar_video -> {
                val intent = Intent(this, Editar_Videojuego::class.java)

                // Pasa los atributos a la actividad EditarVideojuego
                intent.putExtra("id", videojuegoSeleccionado.id)
                intent.putExtra("nombreJ", videojuegoSeleccionado.nombreJ)
                intent.putExtra("disponible", videojuegoSeleccionado.disponible)
                intent.putExtra("plataforma", videojuegoSeleccionado.plataforma)
                intent.putExtra("puntaje", videojuegoSeleccionado.puntaje)
                intent.putExtra("precio", videojuegoSeleccionado.precio)
                intent.putExtra("lanzamiento", videojuegoSeleccionado.lanzamiento.time) // Se pasa el tiempo en milisegundos
                intent.putExtra("generoId", videojuegoSeleccionado.generoId)
                intent.putExtra("nombreGenero", nombreGenero)

                startActivity(intent)
                return true
            }
            R.id.mi_borrar_video -> {
                val respuesta = TiendaBaseDatos.TiendaVideojuego!!.eliminarVideojuego(videojuegoSeleccionado.id)
                eliminarVideojuego(videojuegoSeleccionado.id.toString(),videojuegoSeleccionado.generoId.toString())
                val intent = Intent(this, VideoListView::class.java)
                intent.putExtra("id", videojuegoSeleccionado.generoId)
                intent.putExtra("nombre", nombreGenero)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_videolist), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun eliminarVideojuego(id:String,generoId:String){
        val db = Firebase.firestore
        val referenciaVideojuego = db
            .collection("generos").document(generoId).collection("videojuegos")

        referenciaVideojuego
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { /* Si todo salio bien*/ }
            .addOnFailureListener { /* Si algo salio mal*/ }
    }

} /// me ecuchas ?