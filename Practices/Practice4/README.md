# Practice 4 - Unit 1

## Implements the 5 algorithms for Fibonacci Succession

### Algorithm 1 Top-down recursive version

```scala
def fibonacci(n:Int): Int = {
    if (n<2){
        return n
    }
    else{
        return (fibonacci(n-1) + fibonacci(n-2))
    }
}
```

### Algorithm 2 Explicit formula version

```scala
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
```

### Algorithm 3 Iterative version

```scala
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
```

### Algorithm 4 Iterative with 2 variables
```scala
def fibonacci4(n: Int): Int = {
  var a = 0;
  var b = 1;
  for(k <- Range(0, n)) {
    b = b + a;
    a = b - a;
  }
  return b;
}
```

### Algorithm 5 Iterative 
```scala
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
```