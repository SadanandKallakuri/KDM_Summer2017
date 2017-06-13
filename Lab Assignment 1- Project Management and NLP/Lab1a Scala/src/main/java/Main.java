import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Main {
    protected StanfordCoreNLP pipeline;
    public Main() {
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize,ssplit, pos");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> pos(String documentText)
    {List<String> lemmas = new LinkedList<String>();
        Annotation document = new Annotation(documentText);
        this.pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            String pos= token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            System.out.println(token+":"+pos);
            }
        }
        return lemmas;
    }
    public static void main(String[] args) {
        System.out.println("Starting Stanford POS");
        String text = "This is a simple pos tagger program";
        Main slem = new Main();
        System.out.println(slem.pos(text));
    }

}