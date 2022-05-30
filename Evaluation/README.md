# Evaluation Unit 3
Develop the following instructions in Spark with the Scala programming language.

The objective of this practical test is to try to group customers in specific regions of a wholesale distributor. of a wholesale distributor. This is based on the sales of some product categories.

The data source can be found in the repository:
https://github.com/jcromerohdz/BigData/blob/master/Spark_clustering/Wholesale %20customers%20data.csv

1. Import a single Spark session.

```scala
import org.apache.spark.sql.SparkSession
```
2. Use lines of code to minimize errors.

```scala
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
3. Create an instance of the Spark session.

```scala
val spark = SparkSession.builder().getOrCreate()
```
4. Import the Kmeans library for the clustering algorithm.

```scala
import org.apache.spark.ml.clustering.KMeans
```
5. Load the Wholesale Customers Data dataset.

```scala
val df = spark.read.option("header","true").option("inferSchema","true").csv("Wholesale customers data.csv")
```
6. Select the following columns: Fresh, Milk, Grocery, Frozen, Detergents_Paper, Delicassen and call this feature_data set.

```scala
val feature_data = df.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen")
```
7. Import Vector Assembler and Vector

```scala
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
```
8. Create a new Vector Assembler object for the columns of features as an input set, remembering not to input set, remembering that there are no labels.

```scala
val Vassembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen","Detergents_Paper", "Delicassen")).setOutputCol("features")
```
9. Use the assembler object to transform feature_data.

```scala
val dffeaturedata = Vassembler.transform(feature_data)
```
10. Create a Kmeans model with K=3.

```scala
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(dffeaturedata)
```
11. Evaluate the groups using Within Set Sum of Squared Errors WSSSE and print the centroids.

```scala
val WSSSE = model.computeCost(dffeaturedata)
println(s"Within Set Sum of Squared Errors = $WSSSE")

// Shows the result.
println("Cluster Centers: ")
model.clusterCenters.foreach(println)
```
