import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("Hola mundo")
    // (Inmutables no se reasigna "=")
    val inmutable: String ="Adrian";
    // inmutable = "Vicente";

    // Mutable (Re asignacion)
    var mutable : String ="Vicente";

    mutable = "Adrian";

    // Variables primitivas

    val nombreProfesor : String = "Adrian Eguez"
    val sueldo : Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //clases java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C")-> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if(estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00)
    calcularSueldo(20.00,12.00,20.00)

    // Parametros nombrados
    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00, bonoEspecial = 20.00)

    calcularSueldo(sueldo = 10.00,bonoEspecial = 20.00)
    calcularSueldo(10.00,bonoEspecial = 20.00)

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumarUno = Suma(1,1)
    val sumarDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumarUno.sumar()
    sumarDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos

    // Tipo de arreglos

    //Arreglos estaticos

    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    // Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // For Each -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach {valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }
    // it en ingles eso significa el elemento iterado
    arregloDinamico.forEach { println(it) }

    arregloEstatico.
    forEachIndexed {indice: Int, valorActual: Int ->
        println("Valor ${valorActual} Indice: ${indice}")
    }
    println(respuestaForEach)

    // MAP -> muta el arreglo (Cambia el arreglo)
    // 1) Enviamos el nuevo valor de la iteraccion
    // 2) Nos devuelve es un Arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() +100.00
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    // Or AND
    // AND -> ALL(Todos cumple?)
    // OR -> ANY (Alguno cumple?)
    // V V -> V / V  AND F -> F
    // V OR V -> V / V OR F -> / F OR F ->F

    val respuestaAny: Boolean = arregloDinamico
        .any {valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) // true

    val respuestaAll: Boolean = arregloDinamico
        .all {valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //falsa


    //Reduce -> valor acumulado
    // valor acumulado = 0 (Siempre 0 en lenguaje kotlin )
    // [1,2,3,4,5] -> todos los valores del arreglo

    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulado = 0 -> SIEMPRE EMPIEZA EN 0
                acumulado : Int, valorActual: Int ->
            return@reduce (acumulado +valorActual)// -> Logica negocio
        }
    println(respuestaReduce)


    // filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado

    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            // expresion condicion
            val mayoresACinco: Boolean = valorActual >5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter {it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

}

fun impirmirNombre(nombre: String): Unit{
    // "Nombre: " +variable + "bienvenido";
    println("Nombre : ${nombre}")
}


fun calcularSueldo(
    sueldo: Double, // requerido
    tasa: Double =12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null
): Double{
    // Int -> ? (nullable)
    //string -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa ) + bonoEspecial
    }
}


///////// clases abtraptas con  lenguaje java
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){// bloque de codigo del constructor
        this.numeroUno= uno
        this.numeroDos= dos
        println("Inicializado")
    }
}

// Clase abstracta para constructor primario en lenguaje kotli
abstract class Numeros ( // constructor primario

    // propiedad de la clase protectes numero.numerdos
    protected val numeroUno: Int,

    // propiedad de la clase protectes numero.numerdos
    protected val numeroDos: Int,
){
    //var cedula: string = " " (public es por defecto)
    // private valorcalculado: int =0 (private)

  init{ //bloque codigo constructor primario
      this.numeroUno; this.numeroDos; //this es opcional
      numeroUno; numeroDos; // sin el this, es lo mismo
      println("Inicializando")
  }
}

class Suma ( //constructor primario suma
    uno: Int,
    dos: Int
): Numeros(uno, dos) {
    init { // bloque constructor
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( // Segundo constructor
        uno: Int?,
        dos: Int
    ) : this( // Llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) {// si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//  tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    )
    // Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )

    // public por defecto, o usar private o protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Atributos y Metodos "Compartidos"
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }


}