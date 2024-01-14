package CRUD

import Entidades.GeneroVideojuego
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*


class CrudGeneroJuego() {

    fun obtenerFecha(): Date {
        val formato = SimpleDateFormat("dd/MM/yyyy")
        print("Ingrese la fecha (dd/MM/yyyy): ")
        val fechaStr = readLine() ?: ""
        return formato.parse(fechaStr)
    }
    fun obtenerDatosNuevoGenero(): GeneroVideojuego {
        println("\nIngrese los datos del nuevo género de videojuego:")
        print("Nombre del género: ")
        val nombregenero = readLine() ?: ""
        print("Descripción del género: ")
        val descripgenero = readLine() ?: ""
        print("Cantidad disponible: ")
        val cantidad = readLine()?.toIntOrNull() ?: 0
        print("¿Tiene restricción de edad? (true/false): ")
        val restriccionEdad = readLine()?.toBoolean() ?: false
        val fechaIngreso = obtenerFecha()

        return GeneroVideojuego(nombregenero, descripgenero, cantidad, restriccionEdad, fechaIngreso)
    }

    fun agregarGeneroVideojuego(archivo: String, nuevoGenero: GeneroVideojuego) {
        val file = File(archivo)
        val generosList: MutableList<GeneroVideojuego> = try {
            if (file.exists()) {
                ObjectInputStream(FileInputStream(file)).use { ois ->
                    ois.readObject() as MutableList<GeneroVideojuego>
                }
            } else {
                mutableListOf()
            }
        } catch (e: IOException) {
            mutableListOf()
        } catch (e: ClassNotFoundException) {
            mutableListOf()
        }

        generosList.add(nuevoGenero)

        ObjectOutputStream(FileOutputStream(file)).use { oos ->
            oos.writeObject(generosList)
        }
    }

    fun mostrarGeneros(archivo: String) {
        val file = File(archivo)
        if (!file.exists() || file.length() == 0L) {
            println("No hay datos de géneros de videojuegos para mostrar.")
            return
        }

        val inputStream = ObjectInputStream(FileInputStream(file))

        try {
            val generosList = inputStream.readObject() as MutableList<GeneroVideojuego>
            for (genero in generosList) {
                println(genero)
            }
        } catch (e: IOException) {
            println("Error al leer el archivo: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        } finally {
            inputStream.close()
        }
    }


    fun eliminarGeneroVideojuego(archivo: String, nombreGenero: String) {
        val file = File(archivo)
        val archivoTemporal = File("temp.txt")
        val tempList = mutableListOf<GeneroVideojuego>()

        try {
            if (!file.exists()) {
                println("El archivo de géneros de videojuegos no existe.")
                return
            }

            val inputStream = ObjectInputStream(FileInputStream(file))

            while (true) {
                val genero = inputStream.readObject() as? GeneroVideojuego ?: break
                if (!genero.nombregenero.equals(nombreGenero, ignoreCase = true)) {
                    tempList.add(genero)
                }
            }

            inputStream.close()

            val outputStream = ObjectOutputStream(FileOutputStream(archivoTemporal))

            tempList.forEach {
                outputStream.writeObject(it)
            }

            outputStream.close()

            Files.move(archivoTemporal.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)

            println("El género '$nombreGenero' ha sido eliminado correctamente.")
        } catch (e: EOFException) {
            println("Se alcanzó el final del archivo.")
        } catch (e: IOException) {
            println("Error al eliminar el género: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al leer los objetos del archivo: ${e.message}")
        }
    }

}