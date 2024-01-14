package CRUD

import CrudVideojuego
import java.io.File




fun crearArchivoVideojuegos(archivoVideojuego: String) {
    val archivo = File(archivoVideojuego)

    if (!archivo.exists()) {
        archivo.createNewFile()
        println("Archivo de videojuegos creado: $archivoVideojuego")
    } else {
        println("El archivo de videojuegos ya existe.")
    }
}

fun crearArchivoGeneroJuegos(archivoGenerojuego: String) {
    val file = File(archivoGenerojuego)
    if (!file.exists()) {
        file.createNewFile()
        println("Archivo de géneros de videojuegos creado: $archivoGenerojuego")
    } else {
        println("El archivo de géneros de videojuegos ya existe.")
    }
}


fun main() {

    val crud = CrudVideojuego()
    val crudGeneroJuego = CrudGeneroJuego()

    val nombreArchivoVideojuegos = "datos_videojuegos.txt"
    val nombreArchivoGeneroJuegos = "datos_genero_videojuegos.txt"

    crearArchivoVideojuegos(nombreArchivoVideojuegos)
    crearArchivoGeneroJuegos(nombreArchivoGeneroJuegos)


    var continuar = true // Variable para controlar la ejecución del programa

    while (continuar) {
        println("\n¡Bienvenido al AREA 51 GAMES STORE")

        println("1) Gention de tipo de videojuegos")
        println("2) Gentios de Videojuegos")
        println("3) Salir del sistema")
        println("\n Por favor ingrese una opcion para para continuar")


        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            1 -> {
                var opcionGeneros = 0
                while (opcionGeneros != 5) {
                    println("\nOperaciones para genero:")
                    println("1) Agregar un nuevo tipo de videojuego")
                    println("2) Mostar el listado")
                    println("3) Actualizar los datos")
                    println("4) Borrar un tipo de videojuego")
                    println("5) Regresar")

                    opcionGeneros = readLine()?.toIntOrNull() ?: 0

                    when (opcionGeneros) {
                        1 -> {
                            val nuevoGenero = crudGeneroJuego.obtenerDatosNuevoGenero()
                            crudGeneroJuego.agregarGeneroVideojuego(nombreArchivoGeneroJuegos, nuevoGenero)
                        }
                        2 -> {
                            println("Listado de géneros de videojuegos:")
                            crudGeneroJuego.mostrarGeneros(nombreArchivoGeneroJuegos)
                        }
                        3 -> {
                            // Lógica para actualizar datos de un género
                        }
                        4 -> {
                            println("Ingrese el nombre del género de videojuego a eliminar:")
                            val nombreGenero = readLine() ?: ""
                            crudGeneroJuego.eliminarGeneroVideojuego(nombreArchivoGeneroJuegos, nombreGenero)
                        }
                        5 -> continue
                        else -> println("La opcion ingresada no es valida")
                    }
                }
            }
            2 -> {
                var opcionVideojuegos = 0
                while (opcionVideojuegos != 5) {
                    println("\n")
                    println("Operaciones para Videojuegos:")
                    println("1) Agregar un nuevo videojuego")
                    println("2) Mostrar listado de videojuegos")
                    println("3) Actualizar los datos de un videojuego")
                    println("4) Eliminar la lista de videojuegos")
                    println("5) Regresar")
                    opcionVideojuegos = readLine()?.toIntOrNull() ?: 0

                    when (opcionVideojuegos) {
                        1 -> {
                            val nuevoJuego = crud.obtenerDatosNuevoJuego()
                            crud.agregarVideojuego(nombreArchivoVideojuegos, nuevoJuego)

                        }
                        2 -> {
                            println("\nSeleccione una opción:")
                            println("1) Mostrar todo el listado")
                            println("2) Mostrar por nombre")
                            val opcionMostrar = readLine()?.toIntOrNull() ?: 0

                            when (opcionMostrar) {
                                1 -> {
                                    println("Listado de videojuegos:")
                                    crud.mostrarVideojuegos(nombreArchivoVideojuegos)
                                }
                                2 -> {
                                    println("Ingrese el nombre del videojuego:")
                                    val nombreVideojuego = readLine() ?: ""
                                    crud.mostrarPorNombre(nombreArchivoVideojuegos, nombreVideojuego)
                                }
                                else -> println("La opción ingresada no es válida")
                            }

                        }
                        3 -> {
                            println("Ingrese el nombre del videojuego a modificar:")
                            val nombreModificar = readLine() ?: ""
                            crud.modificarVideojuego(nombreArchivoVideojuegos, nombreModificar)

                        }
                        4 -> {
                            println("Ingrese el nombre del videojuego a eliminar:")
                            val nombreEliminar = readLine() ?: ""
                            crud.eliminarVideojuego(nombreArchivoVideojuegos, nombreEliminar)

                        }
                        5 -> {
                            continue
                        }
                        else -> {
                            println("La opcion ingresada no es valida")
                        }
                    }
                }
            }
            3 -> {
                continuar = false // Salir del bucle y terminar la ejecución
                println("Saliendo del sistema...")
            }
            else -> println("Opción no válida")
        }
    }
}

