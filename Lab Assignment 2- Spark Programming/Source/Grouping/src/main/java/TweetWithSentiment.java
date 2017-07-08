/**
 * Created by Mayanka on 21-Jul-15.
 * Reference : https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo
 */
public class TweetWithSentiment {

        private String line;
        private String cssClass;

        public TweetWithSentiment() {
        }

        public TweetWithSentiment(String line, String cssClass) {
            super();
            this.line = line;
            this.cssClass = cssClass;
        }

        public String getLine() {
            return line;
        }

        public String getCssClass() {
            return cssClass;
        }

        @Override
        public String toString() {
            return "TweetWithSentiment [line=" + line + ", cssClass=" + cssClass + "]";
        }

}
