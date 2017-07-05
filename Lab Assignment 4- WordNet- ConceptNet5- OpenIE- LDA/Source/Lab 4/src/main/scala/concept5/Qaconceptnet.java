package concept5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import  java.io.InputStreamReader;
public class Qaconceptnet {
    public  static void main(String[] args) {

        HttpClient httpClient = new DefaultHttpClient();
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What is used for making pizza");
            String s = br.readLine();
            HttpGet httpGetRequest = new HttpGet("http://conceptnet5.media.mit.edu/data/5.4/search?rel=/r/UsedFor&end=/c/en/"+s+"&limit=10");
            HttpResponse httpResponse = httpClient.execute(httpGetRequest);
            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            System.out.println("----------------------------------------");
            HttpEntity entity = httpResponse.getEntity();
            byte[] buffer = new byte[1024];
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                int bytesRead = 0;
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                while ((bytesRead = bis.read(buffer)) != -1) {
                    String chunk = new String(buffer, 0, bytesRead);
                    System.out.println(chunk);
                    line += chunk;
                }
                inputStream.close();
            }
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(line);
            JSONObject b = (JSONObject) obj;
            JSONArray ja = (JSONArray) b.get("edges");
            for(int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                System.out.println(ob.get("surfaceText"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }


    }
}

