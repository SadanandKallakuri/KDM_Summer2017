package openie
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
object SparkOpenIE {
  def main(args: Array[String]) {
    // Configuration
    val sparkConf = new SparkConf().setAppName("Sparkopenie").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val input1 = sc.textFile("data/a.txt").map(lines => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val t=CoreNLP.returnTriplets(lines)
      t
    })
    //println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
   println(input1.collect().mkString("\n"))



  }

}
