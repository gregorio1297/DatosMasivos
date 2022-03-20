# Evaluation - Unit 1

Answer the following questions with Spark DataFrames and Scala using the "CSV" Netflix_2011_2016.csv found in the spark-dataframes folder.

## 1. Start a simple Spark session.
```scala
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()
```

## 2. Load Netflix Stock CSV file, have Spark infer the data types.
```scala
val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")
```

## 3. What are the column names?
```scala
df.columns
```

## 4. What is the scheme like?
```scala
df.printSchema()
```

## 5. Prints the first 5 columns.
```scala
df.select($"Date",$"Open",$"High",$"Low",$"Close").show()
```

## 6. Use describe () to learn about the DataFrame.
```scala
df.describe().show()
```

## 7. Creates a new dataframe with a new column called "HV Ratio" which is the ratio between the price of the "High" column and the "Volume" column of shares traded for a day. traded for a day. Hint - is a operation
```scala
val df2 = df.withColumn("HV Ratio", df("High")/df("Volume"))
```

## 8. Which day had the highest peak in the "Open" column?
```scala
 val dfday = df.withColumn("Day",dayofmonth(df("Date")))
 val dfdayop = dfday.groupBy("Day").max()
 dfdayop.show()
 dfdayop.select($"Day",$"max(Open)").show()
```

## 9. What is the meaning of the Close column in the context of financial reporting, explain it there is no coding required?
```scala
//The Close column refers to the company action price at the end of the day's closing.
```

## 10. What is the maximum and minimum of the "Volume" column?


## 11. With Scala/Spark $ Syntax answer the following:

//a.How many days was the "Close" column less than $600?
//b.What percentage of the time was the "High" column greater than $500?
//c.What is the Pearson correlation between the "High" column and the "Volume" column?
//d.What is the maximum of the "High" column per year?
//e.What is the average of "Close" column for each calendar month?

