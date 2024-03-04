import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TradeAddress {
    public String[] Address(String[][] dataTrade, long lineCount, int num) throws IOException {

        String[] address = new String[(int) lineCount];
        int a=0;
        for(int i=2; i<num; i++) {
                // URL作って接続
                String urlString = "" + "?zipcode=" + dataTrade[i][4];
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                // JSONデータの読み込み
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String tmp;
                String addressJson = "";
                while ((tmp = in.readLine()) != null) {
                    addressJson += tmp;
                }

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(addressJson);

                if(node.get("results").get(0) != null) {
                    address[a] = node.get("results").get(0).get("address1").asText()
                            + node.get("results").get(0).get("address2").asText()
                            + node.get("results").get(0).get("address3").asText();
                }else{
                    address[a] = node.get("message").asText();
                }

                // 終了処理
                in.close();
                con.disconnect();
                a++;
        }

        return address;
    }
}
