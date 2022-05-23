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

// The data is splitted randomly between train set and test set, the proportion is 70% to 30% as usual.
// The random seed is 1234L, which is a long number data type
val splits = indexed.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

// The network architecture is specified, the input layer must have as many nodes as features there are
// the ouput layer must have as many nodes as classification labels there are.
// The layer in the middle are hidden for the rest of the model and have no relevant restrictions beside logical ones.
// features: [sepal_length,sepal_width,petal_length,petal_width], clasifications: [0.0, 1.0, 2.0] 
val layers = Array[Int](4, 5, 4, 3)

// create the trainer and set its parameters
val trainer = new MultilayerPerceptronClassifier()
 .setLayers(layers)
 .setBlockSize(128)
 .setSeed(1234L)
 .setMaxIter(100)

// train the model
val model = trainer.fit(train)
```
8. Print the results of the model
```scala

// compute accuracy on the test set || precision de calculo en el conjunto de prueba
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator()
 .setMetricName("accuracy")
 
// show the accuracy 
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")

//show the distribution of the data used
println(s"train: ${train.count}, test: ${test.count()}")
//show the table true value vs prediction
result.select("features", "label", "prediction").show(test.count().asInstanceOf[Int])
```

Results:


![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/results_1.png)

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/results_2.png)
