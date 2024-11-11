package codes.domino.screen;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class ScreenReader {
    private final static String mApiKey = "K82996457988957";
    private final static String mLanguage = "eng";
    private final static boolean isOverlayRequired = false;

    private static String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        Iterator<String> itr = params.keys();

        boolean first = true;
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, String.valueOf(StandardCharsets.UTF_8)));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), String.valueOf(StandardCharsets.UTF_8)));
        }
        return result.toString();
    }


    public static JSONObject postOCRRequest(String file) {
        System.out.println("Posting request");
        try {
            URL url = new URL("https://api.ocr.space/parse/image");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            JSONObject postDataParams = new JSONObject();

            postDataParams.put("apikey", mApiKey);
            postDataParams.put("isOverlayRequired", isOverlayRequired);
            postDataParams.put("base64Image", "data:image/png;base64," + file);
            postDataParams.put("language", mLanguage);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(getPostDataString(postDataParams));
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonData = new JSONObject(String.valueOf(response));

            if (jsonData.has("ParsedResults"))
                return jsonData.getJSONArray("ParsedResults").getJSONObject(0);
            else if (jsonData.has("IsErroredOnProcessing")
                    && jsonData.getBoolean("IsErroredOnProcessing"))
                return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
