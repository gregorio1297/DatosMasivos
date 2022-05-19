# Practice 5 Unit 2
# Linear Support Vector Machine

We first import the library to use linear support vector machines.
```scala
import org.apache.spark.ml.classification.LinearSVC
```

The data to be worked with is loaded
```scala
val training = spark.read.format("libsvm").load("C:/Spark/spark-2.4.8-bin-hadoop2.7/data/mlib/sample_libsvm_data.txt)"
```

We select the maximum number of iterationts and normalize the parameter
```scala
 val lsvc = new LinearSVC().setMaxIter(10).setRegParam(0.1)
```

Finally we assign the model and output the coeficients and interecept for linear svc
```scala
val lsvcModel = lsvc.fit(training)
```

Resultados
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Practices/Practice6/practice6_results.png)

As we can see the result of the interception is 0.012.
