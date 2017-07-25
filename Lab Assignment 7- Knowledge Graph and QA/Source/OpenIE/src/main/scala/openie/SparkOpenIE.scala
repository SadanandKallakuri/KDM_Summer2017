package openie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sparkContext = new SparkContext(conf)


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val ipfile = sparkContext.textFile("data/sqad_data").map(s => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val output = MainNLPClass.returnTriplets(s)
      output
    })

    //println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
    println(ipfile.collect().mkString("\n"))


  }

}
