import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv(bank-full.csv)

val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(feature_data)
// we now set the input, output and max categories with featuredata
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(feature_data)
// Split and save the data into training and test data, going with 70% and 30%
val Array(trainingData, testData) = feature_data.randomSplit(Array(0.7, 0.3))

val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

// Convert indexed labels back to normal.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

// Chain indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

// Train the model, this also runs the indexers.
val model = pipeline.fit(trainingData)

// Make the predictions.
val predictions = model.transform(testData)

// Select example rows to display. 
predictions.select("predictedLabel", "label", "features").show(5)

// Select (prediction, true label)
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
// Compute the test error.
val accuracy = evaluator.evaluate(predictions)
println(s"accuracy = ${(accuracy)}")

// Show by stages the classification of the tree model
val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

