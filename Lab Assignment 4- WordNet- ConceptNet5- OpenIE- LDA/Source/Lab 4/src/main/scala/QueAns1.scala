
package openie

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF, Word2Vec, Word2VecModel}
import org.apache.spark.rdd.RDD
import rita.RiWordNet

import scala.collection.immutable.HashMap

/**
  * Created by sadan on 7/1/2017.
  */
object openie {


  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\winutils")
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val docText = sc.textFile("data/sample")
    val dd=docText.map(f=> {
      val wordnet = new RiWordNet("C:\\Users\\sadan\\Desktop\\KDM\\4\\WordNet-3.0")
      val farr = f.split(" ")
      System.out.println("Enter the word to find the synonym")
      val input=scala.io.StdIn.readLine()
      System.out.println("")
      getSynoymns(wordnet, input)
    })
    dd.take(1).foreach(f=>println(f.mkString(" ")))
    val a = openie(docText, sc)




  }



  def openie(docu: RDD[String], sc: SparkContext) {
    val a=docu.map(line => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val t = CoreNLP.returnTriplets(line)
      t


    })


    println(a.collect().mkString("\n"))

  }


  def getSynoymns(wordnet:RiWordNet,word:String): Array[String] ={
    println(word)
    val pos=wordnet.getPos(word)
    println(pos.mkString(" "))
    val syn=wordnet.getAllSynonyms(word, pos(0), 10)
    syn
  }



}
