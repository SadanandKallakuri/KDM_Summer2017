import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;


import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.io.FileUtils;


public class Main {

    protected StanfordCoreNLP pipeline;

    public Main() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

        /*
         * This is a pipeline that takes in a string and returns various analyzed linguistic forms.
         * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator),
         * and then other sequence model style annotation can be used to add things like lemmas,
         * POS tags, and named entities. These are returned as a list of CoreLabels.
         * Other analysis components build and store parse trees, dependency graphs, etc.
         *
         * This class is designed to apply multiple Annotators to an Annotation.
         * The idea is that you first build up the pipeline by adding Annotators,
         * and then you take the objects you wish to annotate and pass them in and
         * get in return a fully annotated object.
         *
         *  StanfordCoreNLP loads a lot of models, so you probably
         *  only want to do this once per execution
         */
        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> lemmatize(String documentText)
    {
        List<String> lemmas = new LinkedList<String>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the
                // list of lemmas
                String[] toppings = new String[20];
                String n=token.get(NamedEntityTagAnnotation.class);
                if(n.equals("PERSON"))
                {
                    try{
                        File log = new File("dataset/per.txt");

                        PrintWriter out = new PrintWriter(new FileWriter(log, true));

                        out.close();
                    } catch (IOException e) {
                        // do something
                    }

                }
                if(n.equals("ORGANIZATION"))
                {
                    try{
                        File log = new File("dataset/org.txt");

                        PrintWriter out = new PrintWriter(new FileWriter(log, true));

                        out.close();
                    } catch (IOException e) {
                        // do something
                    }

                }
                if(n.equals("LOCATION"))
                {
                    try{
                        File log = new File("dataset/loc.txt");

                        PrintWriter out = new PrintWriter(new FileWriter(log, true));

                        out.close();
                    } catch (IOException e) {
                        // do something
                    }

                }
                if(n.equals("DATE"))
                {
                    try{
                        File log = new File("dataset/dates.txt");

                        PrintWriter out = new PrintWriter(new FileWriter(log, true));

                        out.close();
                    } catch (IOException e) {
                        // do something
                    }

                }
                if(n.equals("MISC"))
                {
                    try{
                        File log = new File("dataset/miscellanous.txt");

                        PrintWriter out = new PrintWriter(new FileWriter(log, true));

                        out.close();
                    } catch (IOException e) {
                        // do something
                    }

                }
                //System.out.println(token + ":" + n);



            }
        }
        return lemmas;
    }


    public static void main(String[] args) {
        System.out.println("Starting Stanford Lemmatizer");
        try {
            String line = FileUtils.readFileToString(new File("dataset/input.txt"));
            Main slem = new Main();
            slem.lemmatize(line);
            Scanner scan = new Scanner(System.in);
            String s = scan.next();  // Reading from System.in
            System.out.println("Enter question: ");
            String lin = FileUtils.readFileToString(new File("data/location.txt"));
            System.out.println(lin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}