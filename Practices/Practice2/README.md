# Practice 2 - Unit 1

### 1 Develop an algorithm in scala that calculates the radius of a circle.

```scala
val circun=14
val pi=3.1416
val rad=circun/(2*pi)

print (rad)
```

### 2 Develop an algorithm in scala that tells me if a number is prime.

```scala
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
```

### 3 Given the variable var bird = "tweet", use string interpolation to print "I am writing a tweet".

```scala
var bird = "tweet"
printf("Estoy escribiendo un %s",bird)
```

### 4 Given the variable var message = "Hola Luke yo soy tu padre use slice to extract the sequence "Luke"

```scala
val mensaje = "Hola Luke yo soy tu padre!"
mensaje.slice (5,9)
```

### 5 What is the difference between value (val) and a variable (var) in scala?
```
//The val variable cannot be modified once its value has been predefined.

//The var variable if its values can be overwritten, it can be modified. 
```

### 6 Given the tuple (2,4,5,1,2,3,3,3.1416,23) returns the number 3.1416

```scala
val tupla = (2,4,5,1,2,3,3.1416,23)
tupla._7
```