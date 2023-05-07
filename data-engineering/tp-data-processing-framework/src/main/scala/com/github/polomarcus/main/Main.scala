package com.github.polomarcus.main

import com.github.polomarcus.model.News
import com.typesafe.scalalogging.Logger
import com.github.polomarcus.utils.{ClimateService, NewsService, SparkService}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Main {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    // This is our Spark starting point
    // Open file "src/main/scala/utils/SparkService.scala"
    // Read more about it here : https://spark.apache.org/docs/latest/sql-getting-started.html#starting-point-sparksession
    val spark = SparkService.getAndConfigureSparkSession()
    import spark.implicits._

    // Read a JSON data source with the path "./data-news-json"
    // Tips : https://spark.apache.org/docs/latest/sql-data-sources-json.html
    val pathToJsonData = "./data-news-json/"

    val newsDataframe: DataFrame = spark.read.json(pathToJsonData)

    // To type our dataframe as News, we can use the Dataset API : https://spark.apache.org/docs/latest/sql-getting-started.html#creating-datasets
    val newsDatasets: Dataset[News] = NewsService.read(pathToJsonData)

    // print the dataset schema - tips : https://spark.apache.org/docs/latest/sql-getting-started.html#untyped-dataset-operations-aka-dataframe-operations
    newsDatasets.printSchema()

    // Show the first 10 elements - tips : https://spark.apache.org/docs/latest/sql-getting-started.html#creating-dataframes
    newsDatasets.show(10)

    // Enrich the dataset by apply the ClimateService.isClimateRelated function to the title and the description of a news
    // a assign this value to the "containsWordGlobalWarming" attribute
    val enrichedDataset = NewsService.enrichNewsWithClimateMetadata(newsDatasets)


    // From now, we'll use only the Dataset API as it's more convenient
    val filteredNewsAboutClimate = NewsService.filterNews(enrichedDataset)

    filteredNewsAboutClimate.show()

    
    // Count how many tv news we have in our data source
    val count = NewsService.getNumberOfNews(newsDatasets)
    logger.info(s"We have ${count} news in our dataset")

    // Show how many news we have talking about climate change compare to others news (not related climate)
    // Tips: use a groupBy
    filteredNewsAboutClimate.groupBy("containsWordGlobalWarming").count().show()

    // Use SQL to query a "news" table
    enrichedDataset.createOrReplaceTempView("news")
    //spark.sql("SELECT title FROM news WHERE containsWordGlobalWarming = true").show()

    // Use strongly typed dataset to be sure to not introduce a typo to your SQL Query
    //case class NewsTitle(title: String)
    //val filteredTitles = enrichedDataset.filter(_.containsWordGlobalWarming).map(news => NewsTitle(news.title))
    //filteredTitles.show()
    val filteredTitles = spark.sql("SELECT count(*) as count FROM news WHERE containsWordGlobalWarming=true")
    filteredTitles.show()
    // Save it as a columnar format with Parquet with a partition by date and media
    enrichedDataset.write
      .partitionBy("date", "media")
      .parquet("./enriched-news-parquet")

    logger.info("Stopping the app")
    System.exit(0)
  }
}

