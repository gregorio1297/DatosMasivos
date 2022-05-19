# Practice 5 Unit 2
# Multilayer Perceptron Classifier

## Explanation

```r
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
 
// Load the data stored in LIBSVM format as a DataFrame. || Carga los datos almacenados en formato LIBSVM como DataFrame.
 
//val data = spark.read.format("libsvm").load("data/mllib/sample_multiclass_classification_data.txt")
val data = spark.read.format("libsvm").load("data/sample_multiclass_classification_data.txt")
```

The necessary libraries to use the model are imported, as well as the data set to be used for training and testing. This data set its a vector machine with 4 features and 3 possible classes for classification, these possible classifications are: 0, 1 and 2.

```r
// Split the data into train and test || Divide los datos
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)
```

The data is then splited, this time the proportion is 60% for training and 40% for testing purposes.

```r
// specify layers for the neural network: || especificar capas para la red neuronal:
// input layer of size 4 (features), two intermediate of size 5 and 4 || capa de entrada de tamano 4 (features), dos intermedias de tamano 5 y 4
// and output of size 3 (classes) || y salida de tamano 3 (classes)
val layers = Array[Int](4, 5, 4, 3)
 
// create the trainer and set its parameters || Crea el trainer y establece sus parametros.
val trainer = new MultilayerPerceptronClassifier()
 .setLayers(layers)
 .setBlockSize(128)
 .setSeed(1234L)
 .setMaxIter(100)
```

The schema for the network is described, the input layer has 4 nodes cause there are 4 features in the dataset, the output layer has 3 nodes because there are 3 possible classes / outcomes for the classification. The layers in the middle are transparent for the model and can take any shape desired.

```r
// train the model || entrena el model
val model = trainer.fit(train)
 
// compute accuracy on the test set || precision de calculo en el conjunto de prueba
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator()
 .setMetricName("accuracy")
 
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
```

The model is then trained and a fit function in the trainer is called to do this, using the training data splitted set part. Lastly the error is calculated and shown.

## Results

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Practices/Practice5/practice5_results.png)

As it shows the precision was rather low, with only 90% thats 10% errors in a sample, of course, this precision might be acceptable depending on the usage of the model and the criteria of the tests.
