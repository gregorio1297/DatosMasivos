//1. Desarrollar un algoritmo en scala que calcule el radio de un circulo
val circun=14
val pi=3.1416
val rad=circun/(2*pi)

print (rad)

//2. Desarrollar un algoritmo en scala que me diga si un número es primo
val num: Int = 7 
var primo: Boolean = true
for(v <- Range(2,num)){
    if ((num % v) == 0){
            primo = false
    }
}

if (primo == true){
        print("Numero primo")
    }else{
        print("No es primo")
    } 

//3. Dada la variable  var bird = "tweet", utiliza interpolación de strings para imprimir "Estoy ecribiendo un tweet"
var bird = "tweet"
printf("Estoy escribiendo un %s",bird)

//4. Dada la variable var mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la secuencia "Luke"
val mensaje = "Hola Luke yo soy tu padre!"
mensaje.slice (5,9)

//5. Cúal es la diferencia entre value (val) y una variable (var) en scala?

//La variable val no se puede modificar una vez que ha sido predefinido su valor.

//La variable var si se pueden sobreescribir sus valores, se puede modificar. 

//6. Dada la tupla (2,4,5,1,2,3,3.1416,23) regresa el número 3.1416
val tupla = (2,4,5,1,2,3,3.1416,23)
tupla._7

