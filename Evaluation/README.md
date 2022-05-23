# Evaluation Unit 2

Develop the following instructions in Spark with the Scala programming language, using only the documentation of the Machine Learning Mllib library of Spark and Google. Google.

## Load in a dataframe Iris.csv found at https://github.com/jcromerohdz/iris, elaborate the necessary data cleanup to be processed by the following processed by the following algorithm (Important, this cleanup must be done by a Scala script in by means of a Scala script in Spark) .

1. Use the Mllib library of Spark the Machine Learning multilayer algorithm perceptron

For this we are going to use 5 libraries, 2 for the machine learning algorithm multilayer perceptron classifier, 2 to gather data in a vector with vector assambler and another one to define our index. We load our csv file with the line spark.read.format("csv").option("inferSchema","true").option("header","true").csv("iris.csv") together with some characteristics and the file name

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

For that we use the columns command, we df the variable assigned to our datraframe and dot columns

```scala
df.columns
```
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev1.PNG)

3. What does the schema look like?

Now with df we add printShema()

```scala
df.printSchema()
```
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev2.PNG)

4. Print the first 5 columns.

Here we use the select command together with the 5 columns that this dataframe has and at the end a show to display them.

```scala
df.select($"sepal_length",$"sepal_width",$"petal_length",$"petal_width",$"species").show()
```

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev3.PNG)

5. Use the describe () method to learn more about the DataFrame data.

Here we use the describe command to get a summary of the dataframe both the quantity, mean, variance, min and max.

```scala
df.describe().show()
```
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev4.PNG)

6. Do the relevant transformation for the categorical data which will be our labels to classify our labels to classify.

For this first we have to join 4 columns of our dataframe that is information of flowers, first we are going to join in a vector with the help of the vector assambler for that we use this line where it will join both sepal length and with, petal length and with and will name the column as features. 

We transform the vector with the help of our df and store it in output and create a new df with column features
```scala
val Vassembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val output = Vassembler.transform(df)
output.show()
```
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev5.PNG)

Now we make the transformation of the special column that will be to pass the text data and assign a numerical value, for this we will use the string indexer command and tell the column to be, in our case special and its column name along with the data source that is the df, finally we make a new transformation and another dataframe is created.

```scala
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexSpecies").fit(df)
val indexed = labelIndexer.transform(output) 
indexed.show()
```
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Evaluation/img/ev6.PNG)

7. Build the classification model and explain its architecture.

The data is splitted randomly between train set and test set, the proportion is 70% to 30% as usual.

```scala
// The random seed is 1234L, which is a long number data type
val splits = indexed.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

```

The network architecture is specified, the input layer must have as many nodes as features there are
the ouput layer must have as many nodes as classification labels there are.
The layer in the middle are hidden for the rest of the model and have no relevant restrictions beside logical ones.
features: [sepal_length,sepal_width,petal_length,petal_width], clasifications: [0.0, 1.0, 2.0] 

```scala
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
