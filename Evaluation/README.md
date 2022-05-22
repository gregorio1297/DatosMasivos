# Evaluation Unit 2

Develop the following instructions in Spark with the Scala programming language, using only the documentation of the Machine Learning Mllib library of Spark and Google. Google.

## Load in a dataframe Iris.csv found at https://github.com/jcromerohdz/iris, elaborate the necessary data cleanup to be processed by the following processed by the following algorithm (Important, this cleanup must be done by a Scala script in by means of a Scala script in Spark) .

1. Use the Mllib library of Spark the Machine Learning multilayer algorithm perceptron
```scala
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer 

//Cargar en un dataframe Iris.csv
val df = spark.read.format("csv").option("inferSchema","true").option("header","true").csv("iris.csv")

```
2. What are the names of the columns?
```scala
df.columns
```
3. What does the schema look like?
```scala
df.printSchema()
```
4. Print the first 5 columns.
```scala
df.select($"sepal_length",$"sepal_width",$"petal_length",$"petal_width",$"species").show()
```
5. Use the describe () method to learn more about the DataFrame data.
```scala
df.describe().show()
```
6. Do the relevant transformation for the categorical data which will be our labels to classify our labels to classify.
```scala
//Creamos un vector assambler para hacer la tranformacion del vector
// y poder ser leido por el algoritmo de ML, y combina todas estas columnas

val Vassembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val output = Vassembler.transform(df)
output.show()

//Transformamos la columna especial en numerica y nombrandola como label
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexSpecies").fit(df)
val indexed = labelIndexer.transform(output) 
indexed.show()
```
7. Build the classification model and explain its architecture.
```scala
//Dividmos nuestro data en datos de entrenamiento y datos de prueba 60% para entrenamiento y 40% para prueba 
// seed es para iniciar en algun punto en concreto.
val splits = indexed.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)


//Establecemos las capas de nuestra red neuronal 
// 4 de entreda pasa por 5 y 4 capa intermedia o capa oculta y salida de 3.
val layers = Array[Int](4, 5, 4, 3)
```
8. Print the results of the model
```scala

```