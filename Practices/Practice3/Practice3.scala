// 1, Listas son inmutables, usando listbuffer en su lugar
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

var lista = ListBuffer("rojo", "blanco", "negro")
//2, añadiendo 5 elementos
lista += ("verde", "amarillo", "azul", "naranja", "perla")
//3, getting a slice
var elems = lista.slice(3, 6)

//4, arreglo del 1 al 1000 con paso de 5
val nums = Array.range(1, 1000, 5)

//5, unique elements
val l = List(1, 3, 3, 4, 6, 7, 3, 7)
val uniques = l.toSet

//6, map mut
val nombres = Map("Jose" -> 20, "Luis" -> 24, "Ana" -> 23, "Susana" -> 27)
//6.a. print all keys
println(nombres.keys)
//6.b add relationship
nombres += "Miguel" -> 23