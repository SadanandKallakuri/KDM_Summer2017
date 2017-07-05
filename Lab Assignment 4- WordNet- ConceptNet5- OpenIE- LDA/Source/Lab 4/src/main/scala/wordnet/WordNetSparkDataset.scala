package wordnet
import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet
object WordNetSparkDataset {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils")
    val conf = new SparkConf().setAppName("WordNetSparkDataset").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val dataset=sc.textFile("data/a.txt")
    val syn=dataset.map(g=>{
      val wordnet1 = new RiWordNet("C:\\Users\\sadan\\Desktop\\KDM\\4\\WordNet-3.0")
      val farr1=g.split(" ")
      getSynoymns(wordnet1,"place")
    })
    syn.take(1).foreach(g=>println(g.mkString(" ")))
  }
  def getSynoymns(wordnet1:RiWordNet,word:String): Array[String] ={
    println(word)
    val pos=wordnet1.getPos(word)
    println(pos.mkString(" "))
    val syn=wordnet1.getAllSynonyms(word, pos(0), 10)
    syn
  }
}
