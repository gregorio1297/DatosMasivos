import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer 


val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv("bank-full.csv") //this dataset is separated by ; instead of ,

//We make the array only with the columns that have numbers for the algorith to work more easier
val Vassembler = new VectorAssembler().setInputCols(Array("balance","day","duration","previous")).setOutputCol("features")
val output = Vassembler.transform(df)

//We setup de colum y for our label indexer and renamed to label
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexY").fit(df)
val indexed = labelIndexer.transform(output).withColumnRenamed("indexY", "label") 
indexed.show()


for(i <- 101 to 130) { //101 and 130 are arbitrary values and will loop 30 times

//We make the split, 70% for trainset and 30% por testset
val splits = indexed.randomSplit(Array(0.7, 0.3), seed = i)
val train = splits(0)
val test = splits(1)

//Specify layers for the neural network:
// input layer of size 4 (features), two intermediate of size 5 and 4
// and output of size 2 (classes)

val layers = Array[Int](4, 5, 4, 2)

// create the trainer and set its parameters
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

// train the model
val model = trainer.fit(train)

val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator()
 .setMetricName("accuracy")
 
// show the accuracy with the seed
println(s"seed: $i, Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")

//show the distribution of the data used
println(s"train: ${train.count}, test: ${test.count()}")
//show the table true value vs prediction
result.select("features", "label", "prediction").show(test.count().asInstanceOf[Int])
}
