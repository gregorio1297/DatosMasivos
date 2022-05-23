# Practice 7 Unit 2
# Naive Bayes

Import the necessary libraries

```scala
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
```

Upload data by specifying the file path

```scala
val data = spark.read.format("libsvm").load("C:/spark/spark-2.4.8-bin-hadoop2.7/data/mllib/sample_libsvm_data.txt")
```

Randomly divides the data set into training set and test set according to the weights provided. according to the weights provided. You can also specify a seed, the result is the type of the array, and the array stores the data of type DataSet.

```scala
val Array (trainingData, testData) = data.randomSplit (Array (0.7, 0.3), 100L)
```

Incorporate into the training set (fitting operation) to train a Bayesian model

```scala
val naiveBayesModel = new NaiveBayes().fit(trainingData)
```

The model calls transform() to make predictions and generate a new DataFrame.
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Practices/Practice7/P71.PNG)

Prediction results data output
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Practices/Practice7/P72.PNG)

