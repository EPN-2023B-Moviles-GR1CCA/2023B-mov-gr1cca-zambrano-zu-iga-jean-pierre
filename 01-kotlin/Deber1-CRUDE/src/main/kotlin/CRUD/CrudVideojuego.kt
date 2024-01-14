
import Entidades.videojuegos
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*

class CrudVideojuego() {
    // Función para crear un nuevo videojuego

    fun obtenerFecha(): Date {
        val formato = SimpleDateFormat("dd/MM/yyyy")
        print("Ingrese la fecha de lanzamiento (dd/MM/yyyy): ")
        val fechaStr = readLine() ?: ""
        return formato.parse(fechaStr)
    }

    fun obtenerDatosNuevoJuego(): videojuegos {
        println("\nIngrese los datos del nuevo videojuego:")
        print("Nombre: ")
        val nombre = readLine() ?: ""
        print("Disponible (true/false): ")
        val disponible = readLine()?.toBoolean() ?: false
        print("Plataforma: ")
        val plataforma = readLine() ?: ""
        print("Puntaje: ")
        val puntaje = readLine()?.toIntOrNull() ?: 0
        print("Precio: ")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0
        val lanzamiento = obtenerFecha()

        return videojuegos(nombre, disponible, plataforma, puntaje, precio, lanzamiento)
    }

    fun agregarVideojuego(archivo: String, nuevoVideojuego: videojuegos) {
        val file = File(archivo)
        val videojuegosList: MutableList<videojuegos> = try {
            if (file.exists()) {
                ObjectInputStream(FileInputStream(file)).use { ois ->
                    ois.readObject() as MutableList<videojuegos>
                }
            } else {
                mutableListOf()
            }
        } catch (e: IOException) {
            mutableListOf()
        } catch (e: ClassNotFoundException) {
            mutableListOf()
        }

        videojuegosList.add(nuevoVideojuego)

        ObjectOutputStream(FileOutputStream(file)).use { oos ->
            oos.writeObject(videojuegosList)
        }
    }


    fun mostrarVideojuegos(archivo: String) {
        val file = File(archivo)
        if (!file.exists() || file.length() == 0L) {
            println("No hay datos de videojuegos para mostrar.")
            return
        }

        val inputStream = ObjectInputStream(FileInputStream(file))

        try {
            val videojuegosList = inputStream.readObject() as MutableList<videojuegos>
            for (videojuego in videojuegosList) {
                println(videojuego)
            }
        } catch (e: IOException) {
            println("Error al leer el archivo: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        } finally {
            inputStream.close()
        }
    }


    fun mostrarPorNombre(archivo: String, nombre: String) {
        val file = File(archivo)
        val inputStream = ObjectInputStream(FileInputStream(file))

        var encontrado = false

        try {
            while (true) {
                val objeto = inputStream.readObject()
                if (objeto is ArrayList<*>) {
                    val lista = objeto.filterIsInstance<videojuegos>()
                    val videojuego = lista.find { it.nombre.equals(nombre, ignoreCase = true) }
                    if (videojuego != null) {
                        println("Datos del videojuego con nombre '$nombre':")
                        println(videojuego)
                        encontrado = true
                        break // Terminamos el bucle una vez encontrado el juego
                    }
                }
            }
        } catch (eof: EOFException) {
            // Se alcanzó el final del archivo
            if (!encontrado) {
                println("No se encontró el videojuego con el nombre '$nombre'")
            }
        } catch (e: IOException) {
            println("Error al leer el archivo: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        } finally {
            inputStream.close()
        }
    }

    fun modificarVideojuego(archivo: String, nombreJuego: String) {
        val file = File(archivo)
        val archivoTemporal = File("temp.txt")
        val videojuegosList = mutableListOf<videojuegos>()

        try {
            if (!file.exists()) {
                println("El archivo de videojuegos no existe.")
                return
            }

            val inputStream = ObjectInputStream(FileInputStream(file))

            while (true) {
                val juego = inputStream.readObject() as? videojuegos ?: break
                if (juego.nombre.equals(nombreJuego, ignoreCase = true)) {
                    println("Ingrese los nuevos datos para el juego '$nombreJuego':")
                    println("Nuevo nombre: ")
                    val nuevoNombre = readLine() ?: juego.nombre
                    println("Disponible (true/false): ")
                    val disponible = readLine()?.toBoolean() ?: juego.disponible
                    println("Nueva plataforma: ")
                    val plataforma = readLine() ?: juego.plataforma
                    println("Nuevo puntaje: ")
                    val puntaje = readLine()?.toIntOrNull() ?: juego.puntaje
                    println("Nuevo precio: ")
                    val precio = readLine()?.toDoubleOrNull() ?: juego.precio
                    println("Nueva fecha de lanzamiento (dd/MM/yyyy): ")
                    val lanzamientoStr = readLine() ?: SimpleDateFormat("dd/MM/yyyy").format(juego.lanzamiento)
                    val lanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(lanzamientoStr)

                    val juegoModificado = videojuegos(nuevoNombre, disponible, plataforma, puntaje, precio, lanzamiento)
                    videojuegosList.add(juegoModificado)
                } else {
                    videojuegosList.add(juego)
                }
            }

            inputStream.close()

            val outputStream = ObjectOutputStream(FileOutputStream(archivoTemporal))

            videojuegosList.forEach {
                outputStream.writeObject(it)
            }

            outputStream.close()

            Files.move(archivoTemporal.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)

            println("El juego '$nombreJuego' ha sido modificado correctamente.")
        } catch (e: EOFException) {
            println("Se alcanzó el final del archivo.")
        } catch (e: IOException) {
            println("Error al modificar el juego: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        }
    }


    fun eliminarVideojuego(archivo: String, nombreJuego: String) {
        val file = File(archivo)
        val archivoTemporal = File("temp.txt")
        val tempList = mutableListOf<videojuegos>()

        try {
            if (!file.exists()) {
                println("El archivo de videojuegos no existe.")
                return
            }

            val inputStream = ObjectInputStream(FileInputStream(file))

            while (true) {
                val juego = inputStream.readObject() as? videojuegos ?: break
                if (!juego.nombre.equals(nombreJuego, ignoreCase = true)) {
                    tempList.add(juego)
                }
            }

            inputStream.close()

            val outputStream = ObjectOutputStream(FileOutputStream(archivoTemporal))

            tempList.forEach {
                outputStream.writeObject(it)
            }

            outputStream.close()

            Files.move(archivoTemporal.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)

            println("El juego '$nombreJuego' ha sido eliminado correctamente.")
        } catch (e: EOFException) {
            println("Se alcanzó el final del archivo.")
        } catch (e: IOException) {
            println("Error al eliminar el juego: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        }
    }


/*******************************************/


}


    /*fun eliminarVideojuego(archivo: String, nombreJuego: String) {
        val file = File(archivo)
        val tempList = mutableListOf<videojuegos>()

        try {
            if (!file.exists()) {
                println("El archivo de videojuegos no existe.")
                return
            }

            val inputStream = ObjectInputStream(FileInputStream(file))

            while (true) {
                val juego = inputStream.readObject() as? videojuegos ?: break
                if (juego.nombre.equals(nombreJuego, ignoreCase = true)) {
                    continue // Omite el juego que se va a eliminar
                }
                tempList.add(juego)
            }

            inputStream.close()

            val outputStream = ObjectOutputStream(FileOutputStream(file))

            tempList.forEach {
                outputStream.writeObject(it)
            }

            outputStream.close()

            println("El juego '$nombreJuego' ha sido eliminado correctamente.")
        } catch (e: EOFException) {
            println("Se alcanzó el final del archivo.")
        } catch (e: IOException) {
            println("Error al eliminar el juego: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        }
    }*/






    // Función para mostrar todos los videojuegos


/*

    val listaVideojuegos = mutableListOf<videojuegos>()



    fun crearVideojuego(
        nombre: String, disponible: Boolean, plataforma: String, lanzamiento: Date,
        puntaje: Int, precio: Double
    ) {
        println("Ingrese el nombre del juego:")e
        val nombre = readLine() ?: ""

        val nuevoVideojuego = videojuegos(nombre, disponible, plataforma, lanzamiento, puntaje, precio)
        listaVideojuegos.add(nuevoVideojuego)
        println("¡Videojuego agregado con éxito!")
    }


    /* Ver lista de juegos*/
    fun mostrarVideojuegos() {
        if (listaVideojuegos.isEmpty()) {
            println("No hay videojuegos registrados aún.")
        } else {
            println("Lista de Videojuegos:")
            listaVideojuegos.forEach { println(it) }
        }
    }

    /*Actualizar los datos de juego */
    fun actualizarGenero(nombre: String, nuevojuego: videojuegos){
        val juegos = mostrarVideojuegos.toMutableList()
        val index = videojuegos.indexOfFirst { it.nombre.equals(nombre, ignoreCase = true) }

        if (index!=-1){
            juegos[index] = nuevojuego
            try {

            }
        }
    }


    /*Borrar juego*/


}
 */


