import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.ml.feature.StandardScaler

var data = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .option("delimiter",";") //this dataset is separated by ; instead of ,
  .csv("./bank-full.csv").cache() //caching improves performance

///// PROCESSING OF DATA TO BUILD A LIBSVM FROM THE CSV GIVEN
// The data given in the bank dataset is a csv file, but to work with SVM models data in libsvm format is needed
// so we have to process the data to make it into libvsm format, which is just a dataset containing LabeledPoint's

val cols = Array("age", "feature_job", "feature_marital", "feature_education", "feature_default", "balance", "feature_loan",
  "day", "feature_month", "duration", "campaign", "pdays", "previous", "feature_poutcome")

//columns with values that must be transformed to numbers from discrete variables
val discrete_cols = Array("job","marital", "education", "default", "housing", "loan", "contact", "month", "poutcome", "y")

//Transform discrete variables to numeric ones and rename the cols
val indexer = new StringIndexer()
discrete_cols.foreach{
  case(i) => {
    indexer.setInputCol(i)
    indexer.setOutputCol(s"feature_$i")
    data = indexer.fit(data).transform(data) //apply indexer transformation
    data = data.drop(i) //delete discrete column
  }
}

// collect all independent variables into a features cell
val vecass = new VectorAssembler()
vecass.setInputCols(cols)
vecass.setOutputCol("features")

// dataframe containing the relation (label, features vector)
val label_x_features = vecass.transform(data).select("feature_y", "features")

//data scaling for numeric columns that are not yet features
val scaler = new StandardScaler()
scaler.setInputCol("features")
scaler.setOutputCol("scaled_features")

// apply the scaling to the features
val scaled = scaler.fit(label_x_features).transform(label_x_features).select("feature_y", "scaled_features")

// finally, make a libsvm from the csv given to be used as source for the SVM model
val libsvm = scaled.rdd.map( row => LabeledPoint(
  row.getAs[Double](0), //label is either a 0.0 for no or a 1.0 for yes
  Vectors.fromML(row.getAs[DenseVector](1)) //features
))

///// END OF DATA PROCESSING

// repetition of the runs, changing the seed in every repetition
for(i <- 101 to 130) { //101 and 130 are arbitrary values and will loop 30 times
  val splits = libsvm.randomSplit(Array(0.7, 0.3), seed=i)
  val training = splits(0).cache() //caching helps performance a lot when using big sets of data
  val test = splits(1).cache()

  val model = SVMWithSGD.train(training, 100) //inner var: iterations equals 100

  //test the model and generate the results dataframe
  val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
    val prediction = model.predict(features)
    (prediction, label) //make a new dataset with (prediction_label, real_label)
  }

  //obtain and display metrics, accuracy in this case.
  val metrics = new MulticlassMetrics(predictionAndLabels)
  val accuracy = metrics.accuracy
  println(s"seed: $i, accuracy: $accuracy")
}

