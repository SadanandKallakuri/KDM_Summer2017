import org.apache.spark.{SparkConf, SparkContext}
import java.util.List
import java.util.Map
import java.util.Properties
import java._
import java.util.Arrays
import java.util.Scanner
import java.io.File
import java.io.FileReader
import java.io.File._
import java.io.FileInputStream
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import org.apache.commons.io.FileUtils
import java.util.ArrayList
import scala.io.Source
import java.lang.String
import  java.util.HashMap



/**
 * Created by Mayanka on 23-Jul-15.
 */
object MainClass {
  val nextLine=null;

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("LEMMATIZE").setMaster("local[1]")

    val sc=new SparkContext(sparkConf)
     val contents=sc.textFile("data/input.txt")
    val b = contents.toLocalIterator.mkString

    //for (line <- c) print(line)
    //val contents = scala.io.Source.fromFile("data/input.txt","windows-1252").mkString

    for (line <- contents) print(line)
    // val f = Source.fromFile("data/input.txt","windows-1252").getLines.mkString
    //for (line <- f) print(line)
    val StanfordLemmatizer: StanfordLemmatizer = new StanfordLemmatizer
      val a= StanfordLemmatizer.lemmatize(b)

    System.out.println("lemm"+a)
       val c=sc.parallelize(a.toArray())
       val b1=c.map(f=>(f.toString.head.toUpper,f)).groupByKey()
    System.out.print("output")
    for (line <- b1) println(line)


    val d= c.map(f=>(1,f))




  }
}

