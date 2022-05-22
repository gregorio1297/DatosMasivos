//Utilice la librería Mllib de Spark el algoritmo de Machine Learning multilayer perceptron
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer 

//Cargar en un dataframe Iris.csv
val df = spark.read.format("csv").option("inferSchema","true").option("header","true").csv("iris.csv")

//¿Cuáles son los nombres de las columnas?
df.columns

//¿Cómo es el esquema?
df.printSchema()

//Imprime las primeras 5 columnas.
df.select($"sepal_length",$"sepal_width",$"petal_length",$"petal_width",$"species").show()

//Usa el metodo describe () para aprender mas sobre los datos del DataFrame.
df.describe().show()

//Haga la transformación pertinente para los datos categoricos los cuales serán 
//nuestras etiquetas a clasificar.

//Creamos un vector assambler para hacer la tranformacion del vector
// y poder ser leido por el algoritmo de ML, y combina todas estas columnas

val Vassembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val output = Vassembler.transform(df)
output.show()

//Transformamos la columna especial en numerica y nombrandola como label
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(df) //special note: col must always be called label to be automatic
val indexed = labelIndexer.transform(output) 
indexed.show()


//Construya el modelo de clasificación y explique su arquitectura.

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
