name := "Q2Query"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  //spark
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",

  //coreNLP
  "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models",
  "edu.stanford.nlp" % "stanford-parser" % "3.6.0",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  "org.apache.httpcomponents" % "httpcore" % "4.4.5",
  "org.apache.httpcomponents" % "httpclient" % "4.5.2",
  "com.googlecode.json-simple" % "json-simple" % "1.1.1",
  "com.github.scopt" % "scopt_2.10" % "3.4.0",

  //Owl
  "net.sourceforge.owlapi" % "owlapi-distribution" % "3.4.3",

  //reasoner
  "com.hermit-reasoner" % "org.semanticweb.hermit" % "1.3.8.4"


)

        