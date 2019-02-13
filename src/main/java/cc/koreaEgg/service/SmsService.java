package cc.koreaEgg.service;

import cc.koreaEgg.entity.sms.SmsRequest;
import cc.koreaEgg.entity.sms.SmsSendRequest;
import cc.koreaEgg.entity.sms.SmsSendResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SmsService {

    private final String encodingType = "utf-8";
    private ObjectMapper oMapper = new ObjectMapper();

    public SmsSendResponse sendSms(SmsSendRequest req) throws Exception {

            final String boundary = "____boundary____";
            Map<String, String> sms = oMapper.convertValue(req, Map.class);

            log.trace(sms.toString());

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setBoundary(boundary);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName(encodingType));

            for(Iterator<String> i = sms.keySet().iterator(); i.hasNext();){
                String key = i.next();
                builder.addTextBody(key, sms.get(key)
                        , ContentType.create("Multipart/related", encodingType));
            }

            File imageFile = new File(req.getImagePath());
            if(imageFile.exists()){

                builder.addPart("image",
                        new FileBody(imageFile, ContentType.create("application/octet-stream"),
                                URLEncoder.encode(imageFile.getName(), encodingType)));
            }

            HttpEntity entity = builder.build();

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(req.getSms_url());
            post.setEntity(entity);

            HttpResponse res = client.execute(post);

            String result = "";
            if(res != null){
                BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
                String buffer = null;
                while((buffer = in.readLine())!=null){
                    result += buffer;
                }
                in.close();
            }

            SmsSendResponse response = oMapper.readValue(result,SmsSendResponse.class);
            log.trace(response.toString());
            return response;

    }

    public Object sendSmsInfo(SmsRequest req) throws Exception{

        log.trace(req.toString());

        List<NameValuePair> sms = new ArrayList<NameValuePair>();

        Field[] fields  = req.getClass().getDeclaredFields();  // for axis the fields

        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                sms.add(new BasicNameValuePair(field.getName(), field.get(req).toString())); // SMS 아이디
            }
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(req.getSms_url());
        post.setEntity(new UrlEncodedFormEntity(sms, encodingType));

        HttpResponse res = client.execute(post);

        String result = "";
        if(res != null){
            BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
            String buffer = null;
            while((buffer = in.readLine())!=null){
                result += buffer;
            }
            in.close();
        }

        Object response = oMapper.readValue(result, req.getResponseClass() );
        log.trace(response.toString());
        return response;

    }
}
