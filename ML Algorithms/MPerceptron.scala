import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer 

val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv(bank-full.csv)

val Vassembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val output = Vassembler.transform(df)
output.show()

val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexSpecies").fit(df)
val indexed = labelIndexer.transform(output) 
indexed.show()

val splits = indexed.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

val layers = Array[Int](4, 5, 4, 3)

// create the trainer and set its parameters
val trainer = new MultilayerPerceptronClassifier()
 .setLayers(layers)
 .setBlockSize(128)
 .setSeed(1234L)
 .setMaxIter(100)

// train the model
val model = trainer.fit(train)

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