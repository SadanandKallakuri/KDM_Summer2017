
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import java.util.List;


public class Lemmatisation {

    public static String returnLemma(String sentence) {

        System.out.println("sentence="+sentence);

        Document doc = new Document(sentence);

        String lemma="";

        for (Sentence sent : doc.sentences()) {
            List<String> l=sent.lemmas();
            for (int i = 0; i < l.size() ; i++) {
                lemma+= l.get(i) +" ";
            }
        }
        return lemma;
    }
//    public static void main(String args[]) {
//        String s = "usage is giving good example of lemmatisations";
//        String le= Lemmatisation.returnLemma(s);
//        System.out.println("lemmatised="+le) ;
//    }
}