# Practices - Data engineering

## TP - Data processing with Apache Spark
To process a large amount of data partitioned on a data lake, you can use data processing frameworks such as Apache Spark :
1. Read : https://spark.apache.org/docs/latest/sql-programming-guide.html

Some questions :
* What is Spark RDD API ?

The RDD (Resilient Distributed Datasets) API is the foundational API of Spark, which provides a resilient distributed collection of elements that can be processed in parallel across a cluster of machines. RDDs are immutable and fault-tolerant, meaning they can automatically recover from node failures. The RDD API provides a wide range of transformation and action functions for data processing.
* What is Spark Dataset API ?

The Dataset API is an extension of the DataFrame API, which provides a type-safe, object-oriented programming interface to work with structured data in Spark. The Dataset API provides a similar programming model as RDDs, but with the performance optimizations of DataFrames. Datasets can be used to leverage Spark's optimized execution engine while still providing a strong typing system.
* With which languages can you use Spark ? 

Spark provides APIs for several programming languages, including Scala, Java, Python, R, and SQL. Spark's APIs are available for all these languages, allowing users to leverage the strengths of their preferred programming languages.
* Which data sources or data sinks can Spark work with ? 

Spark can work with a wide range of data sources and data sinks, including Hadoop Distributed File System (HDFS), Apache Cassandra, Apache HBase, Apache Kafka, Amazon S3, and many others. Spark provides built-in connectors to these data sources and also allows users to define their own custom connectors to work with any data source. Additionally, Spark provides several output formats, including CSV, JSON, Parquet, and Avro, among others.
### Analyse data with Apache Spark and Scala 
One engineering team of your company created for you a TV News data stored as JSON inside the folder `data-news-json/`.

Your goal is to analyze it with your savoir-faire, enrich it with metadata, and store it as [a column-oriented format](https://parquet.apache.org/).

1. Look at `src/main/scala/com/github/polomarcus/main/Main.scala` and update the code 

**Important note:** As you work for a top-notch software company following world-class practices, and you care about your project quality, you'll write a test for every function you write.

You can see tests inside `src/test/scala/` and run them with `sbt test`

### How can you deploy your app to a cluster of machines ?
* https://spark.apache.org/docs/latest/cluster-overview.html

### Business Intelligence (BI)
How could use we Spark to display data on a BI tool such as [Metabase](https://www.metabase.com/) ?

Tips: https://github.com/polomarcus/television-news-analyser#spin-up-1-postgres-metabase-nginxand-load-data-to-pg

### Continuous build and test
**Pro Tips** : https://www.scala-sbt.org/1.x/docs/Running.html#Continuous+build+and+test

Make a command run when one or more source files change by prefixing the command with ~. For example, in sbt shell try:
```bash
sbt
> ~ testQuick
```