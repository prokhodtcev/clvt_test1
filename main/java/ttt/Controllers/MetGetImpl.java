package ttt.Controllers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttt.domain.JSOdata;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

@Service
public class MetGetImpl implements MetGet {

    @Autowired
    @Qualifier("settings")
    private Properties Settings;

//    private static final Logger logger = Logger.getLogger(MetGetImpl.class);
    private JSOdata MetaInfo;

    public JSOdata getMeta(){
        return MetaInfo;
    }

    public void GetFromURL() {
        String result;
        try {
            String metaURL = Settings.getProperty("META_URL");
            URL clevertec = new URL(metaURL);
            URLConnection urlConnection = clevertec.openConnection();
            JsonReader in = new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            Gson g = new Gson();
            Type type = new TypeToken<JSOdata>() {
            }.getType();
            MetaInfo = g.fromJson(in, type);
        }catch (Throwable t) {
//            logger.error("Ошибка получения мета данных. " + t.toString());

        }


    }
}
