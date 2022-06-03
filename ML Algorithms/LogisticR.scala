import org.apache.spark.mllib.classification.{LogisticRegressionWithSGD, LogisticRegressionModel}
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
  .csv("./bank-full.csv")
  .cache()


///// PROCESSING OF DATA TO BUILD A LIBSVM FROM THE CSV GIVEN

// all the columns of the dataset, used to generated the features. (the classification column is ignored)
val cols = Array("age", "balance", "day",
"duration", "campaign", "previous", "feature_job",
"feature_marital", "feature_education", "feature_housing",
"feature_loan", "feature_contact", "feature_month", "feature_pdays",
"feature_poutcome")

//columns with values that must be trasnformed to numbers from discrete variables
val discrete_cols = Array("job","marital", "education", "default", "housing", "loan", "contact", "month", "pdays", "poutcome", "y")

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
  row.getAs[Double](0),
  Vectors.fromML(row.getAs[DenseVector](1))
))

///// END OF DATA TRANSFORMATION
for(i <- 101 to 130) { //101 and 130 are arbitrary values and will loop 30 times
    val splits = libsvm.randomSplit(Array(0.7, 0.3), seed=i)
    val training = splits(0).cache()
    val test = splits(1).cache()

    val model = new LogisticRegressionWithSGD()
      .run(training) //train the model


 //test the model and generate the results dataframe
    val predictionAndLabels = test.map{ case LabeledPoint(label, features) => 
        val prediction = model.predict(features)
        (prediction, label) //make a new dataset with (prediction_label, real_label)
    }

    //obtain and display metrics, accuracy in this case.
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val accuracy = metrics.accuracy
    println(s"seed: $i, accuracy: $accuracy")
}
