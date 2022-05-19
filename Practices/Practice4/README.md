# Practice 4 Unit 2
# Gradient-boosted Tree Classifier

## Explanation

The dataset is first loaded from the file system, then a split is made in the data with the proportion 70% to 30%, where 70% of the data is used for training and the remaining 30% for testing.

```r
import org.apache.spark.mllib.tree.GradientBoostedTrees
import org.apache.spark.mllib.tree.configuration.BoostingStrategy
import org.apache.spark.mllib.tree.model.GradientBoostedTreesModel
import org.apache.spark.mllib.util.MLUtils
 
// Load and parse the data file.
val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
// Split the data into training and test sets (30% held out for testing)
val splits = data.randomSplit(Array(0.7, 0.3))
val (trainingData, testData) = (splits(0), splits(1))
```

An object of the class BoostingStrategy is created using default parameters. Its specified that 3 iterations will be made with 2 clases (given that the possible values that the discrete variable can take are 0 and 1). A tree depth of 5 is specified as well.

```r
// Train a GradientBoostedTrees model.
// The defaultParams for Classification use LogLoss by default.
val boostingStrategy = BoostingStrategy.defaultParams("Classification")
boostingStrategy.numIterations = 3 // Note: Use more iterations in practice.
boostingStrategy.treeStrategy.numClasses = 2
boostingStrategy.treeStrategy.maxDepth = 5
```

A model is creating based on the class GradientBoostedTree, feeding from the training data and the object of tree creation strategy seen before. A mapping of the labels is then made using the format (label, prediction).


```r
// Empty categoricalFeaturesInfo indicates all features are continuous.
boostingStrategy.treeStrategy.categoricalFeaturesInfo = Map[Int, Int]()
 
val model = GradientBoostedTrees.train(trainingData, boostingStrategy)
 
// Evaluate model on test instances and compute test error
val labelAndPreds = testData.map { point =>
 val prediction = model.predict(point.features)
 (point.label, prediction)
}
```

Finally, the error is calculated for the training data and the model is stored in the filesystem for later usage.

```r
val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
println(s"Test Error = $testErr")
println(s"Learned classification GBT model:\n ${model.toDebugString}")
 
// Save and load model
model.save(sc, "target/tmp/myGradientBoostingClassificationModel")
val sameModel = GradientBoostedTreesModel.load(sc,
 "target/tmp/myGradientBoostingClassificationModel")
```


## Results
![](https://github.com/gregorio1297/DatosMasivos/blob/Unit2/Practices/Practice4/practice4_results.png)

As it can be seen, the trees generated have predictions that are not as strong as normal decision trees would have, its not concrete but a spectrum, there are 2 leaning points: -1 and 1. The system shows the prediction as a probability value that is either less than 0 or more than 0 for this binary system where the possible outcomes are 0 and 1.
