/**
  * Created by nikky on 6/27/2017.
  */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF, Word2Vec, Word2VecModel}

import scala.collection.immutable.HashMap
import java.io._
object lemma {

    def main(args: Array[String]): Unit = {

      //System.setProperty("hadoop.home.dir", "D:\\Mayanka Lenevo F Drive\\winutils")

      val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

      val sc = new SparkContext(sparkConf)

      //Reading the Text File
      val documents = sc.textFile("data/Article.txt")

      //Getting the Lemmatised form of the words in TextFile
      val documentseq = documents.map(f => {
        val lemmatised = CoreNLP.returnLemma(f)
        val splitString = lemmatised.split(" ")
        splitString.toSeq
      })

      //Creating an object of HashingTF Class
      val hashingTF = new HashingTF()

      //Creating Term Frequency of the document
      val tf = hashingTF.transform(documentseq)
      tf.cache()


      val idf = new IDF().fit(tf)

      //Creating Inverse Document Frequency
      val tfidf = idf.transform(tf)

      val tfidfvalues = tfidf.flatMap(f => {
        val ff: Array[String] = f.toString.replace(",[", ";").split(";")
        val values = ff(2).replace("]", "").replace(")", "").split(",")
        values
      })

      val tfidfindex = tfidf.flatMap(f => {
        val ff: Array[String] = f.toString.replace(",[", ";").split(";")
        val indices = ff(1).replace("]", "").replace(")", "").split(",")
        indices
      })

      tfidf.foreach(f => println(f))

      val tfidfData = tfidfindex.zip(tfidfvalues)

      var hm = new HashMap[String, Double]

      tfidfData.collect().foreach(f => {
        hm += f._1 -> f._2.toDouble
      })

      val mapp = sc.broadcast(hm)

      val documentData = documentseq.flatMap(_.toList)
      val dd = documentData.map(f => {
        val i = hashingTF.indexOf(f)
        val h = mapp.value
        (f, h(i.toString))
      })

      val dd1 = dd.distinct().sortBy(_._2, false)
      val writer = new PrintWriter(new File("out.txt"))

      dd1.take(30) foreach (f => {
        println(f._1)
        writer.write(f._1 + "\n")

      })

      writer.close()
      val d=dd1.collect().take(30)
      val modelFolder = new File("myModelPath")

      val doc = sc.textFile("data/sample").map(line => line.split(" ").toSeq)

      val word2vec = new Word2Vec().setVectorSize(1000)
      val model = word2vec.fit(doc)
      for (line <- scala.io.Source.fromFile("out.txt").getLines()) {

        val synonyms = model.findSynonyms(line, 2)
        System.out.println("Performed Lemmatization,Finding Synonym for-->"+line)
        for ((synonym, cosineSimilarity) <- synonyms) {
          println(s"$synonym $cosineSimilarity")
        }


      }


  }

}
