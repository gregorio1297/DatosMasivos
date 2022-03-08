//Dado el pseudocódigo de la sucesión de Fibonacci en el enlace proporcionado, implementar con Scala el Algoritmo 1, Algoritmo 2, Algoritmo 3, Algoritmo 4, Algoritmo 5

//Algoritmo 1 Versión recursiva descendente

def fibonacci(n:Int): Int = {
    if (n<2){
        return n
    }
    else{
        return (fibonacci(n-1) + fibonacci(n-2))
    }
}

//Algoritmo 2 Versión con fórmula explícita

var p: Double = 0
var j: Double = 0
 
def fibonacci2(n: Double): Double = {
 
    if (n < 2)
        return n
    else {
        p = ((1 + Math.sqrt(5)) / 2)
        j = ((Math.pow(p, n) - Math.pow((1 - p), n)) / Math.sqrt(5))
        return j
    }
}

//Algoritmo 3 Versión iterativa

def fibonacci3(n3: Int):Int ={
  var a = 0
  var b = 1
  var c = 0
  for(k <- Range(0,n3)){
    c = b + a
    a = b
    b = c
  }
  return a
}

//Algoritmo 4, Versión iterativa con 2 variables
def fibonacci4(n: Int): Int = {
  var a = 0;
  var b = 1;
  for(k <- Range(0, n)) {
    b = b + a;
    a = b - a;
  }
  return b;
}

//Algoritmo 5, Versión iterativa con arreglo
def fibonacci5(n: Int): Int = {
  if(n < 2) {
    return n;
  } else {
    var ar = Array.ofDim[Int](n + 1);
    ar(0) = 0;
    ar(1) = 1;
    for(k <- Range(2, n + 1)) {
      ar(k) = ar(k - 1) + ar(k - 2);
    }
    return ar(n);
  }
}