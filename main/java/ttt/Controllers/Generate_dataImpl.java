package ttt.Controllers;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.stream.JsonReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import ttt.domain.JSOdata;
import ttt.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Service
public class Generate_dataImpl implements Generate_data {
    @Autowired
    @Qualifier("settings")
    private Properties Settings;


public String GenerD(JSOdata CLVT_mete)  throws IOException {

    Gson g = new Gson();
    int size=CLVT_mete.fields.size();
    Integer i=0;
    JsonObject o=new JsonObject();
    String s_rez;
    while (i<size){
        switch(CLVT_mete.fields.get(i).type){
            case "TEXT":o.addProperty(CLVT_mete.fields.get(i).name,CLVT_mete.fields.get(i).result);
                break;
            case "LIST":
//                String ri = CLVT_mete.fields.get(i).values.v1;
                o.addProperty(CLVT_mete.fields.get(i).name, CLVT_mete.fields.get(i).result);
                break;
            case "NUMERIC":o.addProperty(CLVT_mete.fields.get(i).name,CLVT_mete.fields.get(i).result);
        }

        i++;
    }
    s_rez = g.toJson(o);
    String s = "{\"form\":"+s_rez+"}";
            System.out.print(s);
    String dataURL= Settings.getProperty("DATA_TO_URL");
    //            URL clevertec_out = new URL("POST",dataURL,"test");
    URL clevertec_out = new URL(dataURL);
    HttpURLConnection url_out= (HttpURLConnection)clevertec_out .openConnection();

//            url_out.setRequestProperty("Content-Length", String.valueOf(s.length()));
            url_out.setConnectTimeout(5000);
//            url_out.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            url_out.setDoOutput(true);
            url_out.setDoInput(true);
    OutputStream os = url_out.getOutputStream();
            os.write(s.getBytes("UTF-8"));
    JsonReader in = new JsonReader(new InputStreamReader(url_out.getInputStream(),"UTF-8"));
    g = new Gson();
    Result r = g.fromJson(in, Result.class);
    return r.result;
}
}
