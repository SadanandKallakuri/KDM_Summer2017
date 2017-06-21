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
  val nextline=null;

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("LEMMASCALA").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    val Lemma: Lemmatizer = new Lemmatizer
    val data1 = sc.textFile("data/input.txt")
    val b = data1.toLocalIterator.mkString.split(" ").map(f=>(f,Lemma.NER(f).toString))
      val person= b.groupBy(_._2)
    //val per2= b.groupBy(_= "[location]")

   //val String2 = String.toLocalIterator.mkString.(f=>f.split(" ")).map(f=>(f,Lemma.NER(f)))

    //for (line1 <- per2) print(line1)

    //per2.foreach(println)


    //val string3=b.map(f=>(f,Lemma.NER(f.toString)))

    //val content = scala.io.Source.fromFile("data/input.txt","windows-1252")


    val whois: util.List[String] = new util.ArrayList[String]
    val whereis: util.List[String] = new util.ArrayList[String]
    val organiztion: util.List[String] = new util.ArrayList[String]
    val date: util.List[String] = new util.ArrayList[String]
    val miscellaneous: util.List[String] = new util.ArrayList[String]




    //for (line 1<- b) print(line1)
    //val content = scala.io.Source.fromFile("data/input.txt","windows-1252").mkString
    //content.collect()
    // val f = Source.fromFile("data/input.txt","windows-1252").getLines
    //for (line1 <- f) print(line1)
    //val Lemmatizer: Lemmatizer = new Lemmatizer
    //val d = Lemmatizer.NER(e)
    //System.out.print(d)





  }}
    //for ( y <- e )
      //{
        //println(y)
      //}


  //}
//}

