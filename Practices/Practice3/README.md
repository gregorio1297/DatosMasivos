# Practice 3 - Unit 1

### imports
```scala
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
```

### 1. Make a list called lista with elements "rojo", "blanco", "negro"
```scala
var lista = ListBuffer("rojo", "blanco", "negro")
```

### 2. Add 5 more elements to lista: "verde", "amarillo", "azul", "naranja", "perla"
```scala
lista += ("verde", "amarillo", "azul", "naranja", "perla")
```

### 3. Slice elements from list: "verde", "amarillo", "azul".
```scala
var elems = lista.slice(3, 6)
```

### 4. Make an array with numbers in range 1-1000 with a step of 5
```scala
val nums = Array.range(1, 1000, 5)
```

### 5. Get the unique elements in the List (1, 3, 3, 4, 6, 7, 3, 7), use set conversion
```scala
val l = List(1, 3, 3, 4, 6, 7, 3, 7)
val uniques = l.toSet
```

### 6. Make a mutable Map called nombres with the elemens "Jose" -> 20, "Luis" -> 24, "Ana" -> 23, "Susana" -> 27.
```scala
val nombres = Map("Jose" -> 20, "Luis" -> 24, "Ana" -> 23, "Susana" -> 27)
```

### 6.a Print all keys in the map
```scala
println(nombres.keys)
```

### 6.b Add the following key-value pair: "Miguel" -> 23
```scala
nombres += "Miguel" -> 23
```