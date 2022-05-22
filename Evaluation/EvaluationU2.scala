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
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexSpecies").fit(df)
val indexed = labelIndexer.transform(output) 
indexed.show()


//Construya el modelo de clasificación y explique su arquitectura.

//Dividmos nuestro data en datos de entrenamiento y datos de prueba 60% para entrenamiento y 40% para prueba 
// seed es para iniciar en algun punto en concreto.
val splits = indexed.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)


//Establecemos las capas de nuestra red neuronal 
// 4 de entreda pasa por 5 y 4 capa intermedia o capa oculta y salida de 3.
val layers = Array[Int](4, 5, 4, 3)

//Imprima los resultados del modelo