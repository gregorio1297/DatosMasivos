# Evaluation Unit 3
Develop the following instructions in Spark with the Scala programming language.

The objective of this practical test is to try to group customers in specific regions of a wholesale distributor. of a wholesale distributor. This is based on the sales of some product categories.

The data source can be found in the repository:
https://github.com/jcromerohdz/BigData/blob/master/Spark_clustering/Wholesale %20customers%20data.csv

1. Import a single Spark session.

A spark sesion is started.
```scala
import org.apache.spark.sql.SparkSession
```
2. Use lines of code to minimize errors.

Logger is a class that controls the logging of event in the spark session.
```scala
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
3. Create an instance of the Spark session.

A new instance of a spark session is created.

```scala
val spark = SparkSession.builder().getOrCreate()
```
4. Import the Kmeans library for the clustering algorithm.

The clustering package has some other clustering methods, we will be using KMeans exclusively.

```scala
import org.apache.spark.ml.clustering.KMeans
```
5. Load the Wholesale Customers Data dataset.

The data in the dataset describe the sales of a store, classified as Fresh produce, milk, detergent, groceries, etc.

```scala
val df = spark.read.option("header","true").option("inferSchema","true").csv("Wholesale customers data.csv")
```
6. Select the following columns: Fresh, Milk, Grocery, Frozen, Detergents_Paper, Delicassen and call this feature_data set.

Here only the revenue features are selecting, leaving channel and region besides.

```scala
val feature_data = df.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen")
```

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit3/Evaluation/imgs/ev31.PNG)

7. Import Vector Assembler and Vector

For vector manipulation made easy.

```scala
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
```
8. Create a new Vector Assembler object for the columns of features as an input set, remembering not to input set, remembering that there are no labels.

```scala
val Vassembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen","Detergents_Paper", "Delicassen")).setOutputCol("features")
```

9. Use the assembler object to transform feature_data.

A new table is made where there is an extra column showing all the features for every row.

```scala
val dffeaturedata = Vassembler.transform(feature_data)
```

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit3/Evaluation/imgs/ev32.PNG)

10. Create a Kmeans model with K=3.

The k means there will be k clusters, in this case 3 clusters, with a random number set. Once the kmeans obeject is made we proceed to make a model using the fitting of the previous kmeans object with the dffeaturedata as the dataset of origin

```scala
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(dffeaturedata)
```
11. Evaluate the groups using Within Set Sum of Squared Errors WSSSE and print the centroids.

This method allows to calculate the distance between the correct data and the predicting data giving as a result a percentage of the error.

```scala
val WSSSE = model.computeCost(dffeaturedata)
println(s"Within Set Sum of Squared Errors = $WSSSE")
```

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit3/Evaluation/imgs/ev33.PNG)

The centroids represent the point where belonging to a specific cluster is definitive, while a point that is farther from this centroid is less likely to be part of the cluster. For every data row a distance will be determined from the data to the centroid of every cluster and the closest cluster will be the cluster assigned to said data.

```scala
// Shows the result.
println("Cluster Centers: ")
model.clusterCenters.foreach(println)
```

![](https://github.com/gregorio1297/DatosMasivos/blob/Unit3/Evaluation/imgs/ev34.PNG)
