/**
 * Created by Mayanka on 20-Jul-15.
 * Reference : https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo
 */

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class SentimentAnalyzer {

    public TweetWithSentiment findSentiment(String line) {

      Properties props = new Properties();
      props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
      int mainSentiment = 0;
      if (line != null && line.length() > 0) {
        int longest = 0;
        Annotation annotation = pipeline.process(line);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
          Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
          int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
          String partText = sentence.toString();
          if (partText.length() > longest) {
            mainSentiment = sentiment;
            longest = partText.length();
          }

        }
      }
      if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
        return null;
      }

      TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
      return tweetWithSentiment;

    }
    public int findSentiment(String line,int i) {
    Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            int mainSentiment = 0;
            if (line != null && line.length() > 0) {
                int longest = 0;
                Annotation annotation = pipeline.process(line);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                    String partText = sentence.toString();
                    if (partText.length() > longest) {
                        mainSentiment = sentiment;
                        longest = partText.length();
                    }

                }
            }
            if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
                return -1;
            }

            return mainSentiment;


    }

    private String toCss(int sentiment) {
        switch (sentiment) {
            case 0:
                return "sentiment : very negative";
            case 1:
                return "sentiment : negative";
            case 2:
                return "sentiment : neutral";
            case 3:
                return "sentiment : positive";
            case 4:
                return "sentiment : very positive";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        TweetWithSentiment tweetWithSentiment = sentimentAnalyzer
                .findSentiment("click here for your Sachin Tendulkar personalized digital autograph.");
        System.out.println(tweetWithSentiment);
    }
}
