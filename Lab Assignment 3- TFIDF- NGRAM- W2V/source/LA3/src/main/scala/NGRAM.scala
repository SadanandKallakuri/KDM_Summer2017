import java.io.File

import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 19-06-2017.
  */
object NGRAM {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    //Reading the Text File
    val documents = sc.textFile("data/sample").toLocalIterator.mkString
    val a = getNGrams(documents,2)
      //val k=sc.parallelize(a)
      //k.saveAsTextFile("ngram").toString
    a.foreach(f=>println(f.mkString(" ")))
    val modelFolder = new File("myModelPath")

    val doc = sc.textFile("data/sample").map(line => line.split(" ").toSeq)

    val word2vec = new Word2Vec().setVectorSize(500)
    val model = word2vec.fit(doc)
    for (line <- scala.io.Source.fromFile("ngram").getLines()) {

      val synonyms = model.findSynonyms(line, 2)
      System.out.println("Performed Ngram,Finding Synonym for-->"+line)
      for ((synonym, cosineSimilarity) <- synonyms) {
        println(s"$synonym $cosineSimilarity")
      }


    }

  }

  def getNGrams(sentence: String, n:Int): Array[Array[String]] = {
    val words = sentence
    val ngrams = words.split(' ').sliding(n)
    ngrams.toArray
  }

}


