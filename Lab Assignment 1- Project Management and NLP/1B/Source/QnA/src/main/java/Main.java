import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.File.*;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import java.util.ArrayList;




public class Main {
    public static void main(String args[]) {
        String que = new String();//question variable

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        Properties qprops = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String nextLine = null;

        try {
            String line = FileUtils.readFileToString(new File("dataset/input.txt"));
            nextLine = line;


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(nextLine);
        String par1 = new String();
        String par2 = new String();
        String par3 = new String();
        String par4 = new String();
        String par5 = new String();
        String par6 = new String();


        List<String> who = new ArrayList<String>();
        List<String> where = new ArrayList<String>();
        List<String> org = new ArrayList<String>();
        List<String> time = new ArrayList<String>();
        List<String> misc = new ArrayList<String>();


        Annotation document = new Annotation(nextLine);

// run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                System.out.println("\n" + token);

                // this is the text of the token

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println(token + ":" + ne);
                if (ne.contentEquals("PERSON")) {
                    who.add(token + ":" + ne);
                }
                if (ne.contentEquals("MISC")) {
                    misc.add(token + ":" + ne);
                }
                if (ne.contentEquals("LOCATION")) {
                    where.add(token + ":" + ne);
                }
                if (ne.contentEquals("ORGANIZATION")) {
                    org.add(token + ":" + ne);
                }
                if (ne.contentEquals("TIME")) {
                    time.add(token + ":" + ne);
                }

            }

            for (int i = 0; i < who.size(); i++) {
                System.out.print(who.get(i));
            }
            System.out.println("size" + who.size());
            for (int i = 0; i < where.size(); i++) {
                System.out.print("places"+where.get(i));
            }

            System.out.print("placessize"+where.size());

            for (int i = 0; i < org.size(); i++) {
                System.out.print(org.get(i));
            }
            for (int i = 0; i < time.size(); i++) {
                System.out.print(time.get(i));
            }
            for (int i = 0; i < misc.size(); i++) {
                System.out.print(misc.get(i));
            }


            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);
            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(dependencies.toString());

            Map<Integer, CorefChain> graph =
                    document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(graph.values().toString());
        }
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome to Query ME!!!");
            System.out.println("Fire your Question");
            que = scan.nextLine();
            System.out.println("The Question Entered is: " + que);
            //reading question done till here.
        } catch (Exception e) {
            System.out.print("exception");
        }


        qprops.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipe = new StanfordCoreNLP(qprops);

        Annotation document1 = new Annotation(que);

// run all Annotators on this text
        pipeline.annotate(document1);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences1 = document1.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences1) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token1 : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String pos = token1.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println(token1 + "....>" + pos);
                if (pos.contentEquals("WP")) {
                    par1 = pos;//who,what
                    System.out.println("par1" + par1);
                } else if (pos.contentEquals("WRB")) {
                    par2 = pos;// where,when,how

                } else if (pos.contentEquals("WDT")) {
                    par3 = pos;// Which
                    System.out.println("par3---" + par3);


                } else if (pos.contentEquals("NNS")) {
                    par4 = pos;
                    par5 = token1.toString();
                    System.out.println("par5---" + par5);


                } else if (pos.contentEquals("NN")) {
                    par4 = pos;
                    par5 = token1.toString();
                    System.out.println("par5---" + par5);


                }

                if (par2.contentEquals("WRB")) {
                    if (par4.contentEquals("NN")) {
                        if (par5.toUpperCase().contentEquals("PERSON-3")) {
                            System.out.print("Number of persons are:::" + who.size());
                        }
                        if (par5.toUpperCase().contentEquals("LOCATION-3")) {
                            par6 = "There are " + where.size() + " Places";
                            System.out.print("Number of places are:" + where.size());
                            System.out.println("The places are");
                            for (int i = 0; i < org.size(); i++) {
                                System.out.println(org.get(i));
                            }


                        }
                        if (par5.toUpperCase().contentEquals("ORGANIZATION-3")) {
                            par6 = "There are " + org.size() + " Organizations";
                            System.out.print("Number of organizations are:" + org.size());
                            System.out.println("\n");
                            System.out.println("The organisations listed are");
                            for (int i = 0; i < org.size(); i++) {
                                System.out.println(org.get(i));
                            }
                        }

                    }
                }


                if (par1.contentEquals("WP")) {
                    System.out.println("entered 1");
                    if (par4.contentEquals("NN")) {
                        System.out.println("entered 2");

                        if (par5.toUpperCase().contentEquals("PERSON-4")) {
                            System.out.println("The persons present are \n " + who.size() );
                            System.out.print(who);
                        }
                        if (par5.toUpperCase().contentEquals("LOCATION-4")) {
                            System.out.println("There are " + where.size() + " Places listed \n");
                            System.out.println("The places are");
                            for (int i = 0; i < org.size(); i++) {
                                System.out.println(where.get(i));
                            }
                        }
                        if (par5.toUpperCase().contentEquals("ORGANIZATION-4")) {
                            System.out.println("There are " + org.size() + " Organization \n");
                            System.out.println("The organizations are:");

                            for (int i = 0; i < org.size(); i++) {
                                System.out.println(org.get(i));
                            }
                        }
                    }
                }
                if (par3.contentEquals("WDT")) {
                    if (par4.contentEquals("NN")) {

                        if (par5.toUpperCase().contentEquals("MISC-4")) {
                            System.out.print("MISC elements size is:::" + misc.size());
                            System.out.print("\n");

                        }
                        for (int i = 0; i < misc.size(); i++) {
                            System.out.println("\n");
                            System.out.print(misc.get(i));
                        }
                    }


                }

            }

        }
    }
}




// read some text in the text variable
// create an empty Annotation just with the given text
