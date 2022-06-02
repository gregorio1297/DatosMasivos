# Final Proyect

* Guzman Morales Gregorio Manuel
* Regalado Lopez Edgar Eduardo

# Index
* Introduction
* Theoretical framework
* Implementation
* Results
* Conclusion
* References

# Introduction
The final project of the subject of big data will be presented, which consists of explaining certain machine learning algorithms in which SVM, Decision Trees, Multilayer perceptron and Logistic Regression are found, each of them will be implemented in a file with thousands of data to see its performance, operation and finally we will give a verdict of which is the most efficient and the one that gives the best results. 

# Theoretical framework
## Support Vector Machine(SVM)
A Support Vector Machine (SVM) learns the decision surface of two distinct classes of input points. As a one-class classifier, the description given by the support vector data is able to form a decision boundary around the domain of the learning data with little or no knowledge of the data outside this boundary. 

![](https://github.com/gregorio1297/DatosMasivos/blob/FinalProyect/IMGS/svm.PNG)

The data is mapped by means of a Gaussian or other kernel to a feature space in a higher dimensional space, where the maximum separation between classes is sought. This boundary function, when brought back to the input space, can separate the data into all distinct classes, each forming a clustering.

## Decision Three
A decision tree is a prediction model whose main objective is inductive learning from observations and logical constructions. A tree is graphically represented by a set of nodes, leaves and branches. The main node or root is the attribute from which the classification process starts; the internal nodes correspond to each of the questions about the particular attribute of the problem. Each possible answer to the questions is represented by a child node. The branches coming out of each of these nodes are labeled with the possible values of the attribute.

![](https://github.com/gregorio1297/DatosMasivos/blob/FinalProyect/IMGS/three.PNG)

The final nodes or leaf nodes correspond to a decision, which coincides with one of the class variables of the problem to be solved. 

## Logistic Regression
Logistic regression is a statistical tool for multivariate analysis, for both explanatory and predictive use. It is useful when there is a dichotomous dependent variable (an attribute whose absence or presence we have scored with the values zero and one, respectively) and a set of predictor or independent variables, which can be quantitative (called covariates or covariates) or categorical. In the latter case, they are required to be transformed into dummy variables.

![](https://github.com/gregorio1297/DatosMasivos/blob/FinalProyect/IMGS/logr.PNG)

The purpose of the analysis is: to predict the probability of a certain "event" happening to someone: e.g., being unemployed = 1 or not unemployed = 0, being poor = 1 or not poor = 0, graduating as a sociologist = 1 or not graduating = 0).

## Multilayer perceptron
The multilayer perceptron is a unidirectional neural network consisting of three or more layers: an input layer, an output layer and the rest of the intermediate layers called hidden layers. The type of learning of this network is supervised and is done by means of the algorithm called backpropagation of errors (backpropagation). This network, together with its learning algorithm, is the most widely used model in practical applications.

![](https://github.com/gregorio1297/DatosMasivos/blob/FinalProyect/IMGS/mulp.PNG)

# Implementation
The following tools were used to carry out this project:
* Spark

It is an open source parallel processing platform that supports in-memory processing to improve the performance of big data analytics applications. The Spark processing engine is built for speed, ease of use and sophisticated analytics. Spark's distributed in-memory computing capabilities make it a good choice for iterative algorithms in graph computation and machine learning.
* Scala

Scala combines functional and object-oriented programming in a concise, high-level language. Scala's static types help avoid bugs in complex applications, and its JVM and JavaScript runtimes allow you to build high-performance systems with easy access to huge library ecosystems.

# Results
# Conclusion

# References
* BETANCOURT, G. . A. . (2005). LAS MÁQUINAS DE SOPORTE VECTORIAL (SVMs). Scientia Et Technica, 1(27). https://doi.org/10.22517/23447214.6895 

* Barrientos, R. (2009). Árboles de decisión como herramienta en el diagnóstico médico. Universidad Veracruzana. Recuperado 1 de junio de 2022, de http://www.soporte.uv.mx/rm/num_anteriores/revmedica_vol9_num2/articulos/arboles.pdf

* Chitarroni, H. (2002). La regresión logística. Instituto de Investigación en Ciencias Sociales. Recuperado 1 de junio de 2022, de https://racimo.usal.edu.ar/83/1/Chitarroni17.pdf

* Vivas, H. (2014). Optimización en el entrenamiento del Perceptrón Multicapa. Facultad de Ciencias Naturales, Exactas y de la Educación. Recuperado 1 de junio de2022,dehttp://matematicas.unicauca.edu.co/investigacion/gedi/optimizacion/TesisVivas.pdf 

* M. (2022, 16 abril). Ciencia de datos mediante Scala y Spark en Azure - Azure Architecture Center. Microsoft Docs. Recuperado 1 de junio de 2022, de https://docs.microsoft.com/es-es/azure/architecture/data-science-process/scala-walkthrough 

* The Scala Programming Language. (s. f.). Scala. Recuperado 1 de junio de 2022, de https://www.scala-lang.org 