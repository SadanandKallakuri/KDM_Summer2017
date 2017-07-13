package kMeans

import java.io.PrintStream

import classification.CoreNLP
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

import scala.collection.immutable.HashMap

object tfnlp {


  def main(args: Array[String]): Unit = {
    //System.setProperty("hadoop.home.dir", "C:\\winutils")
    val conf = new SparkConf().setAppName(s"KMeansExample").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val inputPath=Seq("data/20_news/*")
    Logger.getRootLogger.setLevel(Level.WARN)

    val topic_output = new PrintStream("data/tfnlp.txt")
    // Load documents, and prepare them for KMeans.
    val preprocessStart = System.nanoTime()
    val (corpusVector, data, vocabSize) = preprocess(sc, inputPath)
    val a=corpusVector
    println("ooooo")
    for(lines<-a)
    {
      System.out.println(lines)
    }

    val actualCorpusSize = corpusVector.count()
    val actualVocabSize = vocabSize
    val preprocessElapsed = (System.nanoTime() - preprocessStart) / 1e9

    println()
    println(s"Corpus summary:")
    println(s"\t Training set size: $actualCorpusSize documents")
    println(s"\t Vocabulary size: $actualVocabSize terms")
    println(s"\t Preprocessing time: $preprocessElapsed sec")
    println()


    topic_output.println()
    topic_output.println(s"Corpus summary:")
    topic_output.println(s"\t Training set size: $actualCorpusSize documents")
    topic_output.println(s"\t Vocabulary size: $actualVocabSize terms")
    topic_output.println(s"\t Preprocessing time: $preprocessElapsed sec")
    topic_output.println()

    // Run KMeans.
    val startTime = System.nanoTime()

    val k= 5
    val numIterations=20

    val corpusKM=corpusVector.map(_._2)
    System.out.println("corpusKM")
    for(lines<-corpusKM)
    {
      System.out.println(lines)
    }

    val model = KMeans.train(corpusKM, k, numIterations)



    val elapsed = (System.nanoTime() - startTime) / 1e9

    println(s"Finished training KMeans model.  Summary:")
    println(s"\t Training time: $elapsed sec")


    topic_output.println(s"Finished training KMeans model.  Summary:")
    topic_output.println(s"\t Training time: $elapsed sec")

    val predictions = model.predict(corpusKM)
    System.out.println("predictions")

    val error = model.computeCost(corpusKM)
    val results = data.zip(predictions)

    val resultsA = results.collect()
    System.out.println("resultsa")
    for(lines<-resultsA)
    {
      System.out.println(lines)
    }
    var hm = new HashMap[Int, Int]
    resultsA.foreach(f => {
      topic_output.println(f._1._1 +";" + f._2)

      if (hm.contains(f._2)) {
        var v = hm.get(f._2).get
        v = v + 1
        hm += f._2 -> v
      }
      else {
        hm += f._2 -> 1
      }
    })

    topic_output.close()
    sc.stop()

  }

  /**
    * Load documents, tokenize them, create vocabulary, and prepare documents as term count vectors.
    *
    * @return (corpus, vocabulary as array, total token count in corpus)
    */
  private def preprocess(sc: SparkContext,paths: Seq[String]): (RDD[(Long, Vector)], RDD[(String,String)], Long) = {

    //Reading Stop Words
    val stopWords=sc.textFile("data/stopwords.txt").collect()
    System.out.println("stopwords")

    val stopWordsBroadCast=sc.broadcast(stopWords)
    val fd = sc.wholeTextFiles(paths.mkString(","))

    val df = sc.wholeTextFiles(paths.mkString(",")).map(f => {
      //System.out.println("f._1"+f._1)


      //System.out.println("f._2"+f._2)
      val lemmatised=CoreNLP.returnLemma(f._2)
      val splitString = lemmatised.split(" ")
      (f._1,splitString)
    })

    val stopWordRemovedDF=df.map(f=>{
      //Filtered numeric and special characters out
      val filteredF=f._2.map(_.replaceAll("[^a-zA-Z]",""))
        //Filter out the Stop Words
        .filter(ff=>{
        if(stopWordsBroadCast.value.contains(ff.toLowerCase))
          false
        else
          true
      })
      (f._1,filteredF)
    })

    val data=stopWordRemovedDF.map(f=>{(f._1,f._2.mkString(" "))})

    val dfseq=stopWordRemovedDF.map(_._2.toSeq)

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF(stopWordRemovedDF.count().toInt)  // VectorSize as the Size of the Vocab

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(dfseq)
    System.out.println("tf")
    tf.take(10).foreach(println)
    tf.cache()

    val idf = new IDF().fit(tf)
    tf.cache()
    //Creating Inverse Document Frequency
    val tfidf1 = idf.transform(tf)
    System.out.println("tfidf1")
    tfidf1.take(10).foreach(println)
    tfidf1.cache()


    val tfidf=tfidf1.zipWithIndex().map(_.swap)
    val tf1=tf.zipWithIndex().map(_.swap)
    System.out.println("tfidf"+tfidf)
    tfidf.take(10).foreach(println)

    val dff= stopWordRemovedDF.flatMap(f=>f._2)

    val vocab=dff.distinct().collect()
    tfidf.collect()
    (tf1, data, dff.count()) // Vector, Data, total token count
  }

}
