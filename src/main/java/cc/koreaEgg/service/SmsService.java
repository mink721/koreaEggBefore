package cc.koreaEgg.service;

import cc.koreaEgg.entity.AuthNum;
import cc.koreaEgg.entity.ContactUs;
import cc.koreaEgg.entity.sms.SmsRequest;
import cc.koreaEgg.entity.sms.SmsSendRequest;
import cc.koreaEgg.entity.sms.SmsSendResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.*;
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
    private String koreaEggMobile = "01055664855";
    private String adminMobile = "01080790179";
    private String adminTitle = "[코리아에그 웹 에러]";

    public SmsSendResponse sendSms(SmsSendRequest req) {

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

                try {
                    builder.addPart("image",
                            new FileBody(imageFile, ContentType.create("application/octet-stream"),
                                    URLEncoder.encode(imageFile.getName(), encodingType)));
                } catch (UnsupportedEncodingException e) {
                    throw new DataIntegrityViolationException(e.getMessage());
                }
            }

            HttpEntity entity = builder.build();

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(req.getSms_url());
            post.setEntity(entity);

        HttpResponse res = null;
        try {
            res = client.execute(post);
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
        } catch (IOException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    public Object sendSmsInfo(SmsRequest req){

        log.trace(req.toString());

        List<NameValuePair> sms = new ArrayList<NameValuePair>();

        Field[] fields  = req.getClass().getDeclaredFields();  // for axis the fields

        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    sms.add(new BasicNameValuePair(field.getName(), field.get(req).toString())); // SMS 아이디
                } catch (IllegalAccessException e) {
                    throw new DataIntegrityViolationException(e.getMessage());
                }
            }
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(req.getSms_url());

        try {
            post.setEntity(new UrlEncodedFormEntity(sms, encodingType));
            HttpResponse res = client.execute(post);
            String result = "";
            if(res != null){
                InputStreamReader rd = new InputStreamReader(res.getEntity().getContent(), encodingType);
                BufferedReader in = new BufferedReader(rd);
                String buffer = null;
                while((buffer = in.readLine())!=null){
                    result += buffer;
                }
                in.close();
            }

            Object response = oMapper.readValue(result, req.getResponseClass() );
            log.trace(response.toString());
            return response;
            /*TODO throw 수정*/
        } catch (UnsupportedEncodingException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        } catch (ClientProtocolException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        } catch (IOException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    public void contacUsSms(ContactUs cu){
        SmsSendRequest req = new SmsSendRequest();

        req.setReceiver(koreaEggMobile);
        req.setTitle("문의 [" + cu.getTitle() +"]");
        req.setMsg("");

        SmsSendResponse res = sendSms(req);
        alertAdmin(res);

    }

    private void alertAdmin(SmsSendResponse res){
        if( res.getResult_code() != 1 ){
            SmsSendRequest adminReq = new SmsSendRequest();
            adminReq.setReceiver(adminMobile);
            adminReq.setTitle(adminTitle);
            sendSms(adminReq);
        }
    }

    public void authSms(AuthNum auth){

        SmsSendRequest req = new SmsSendRequest();

        req.setReceiver(auth.getPhone());
        req.setTitle("[코리아에그]");
        req.setMsg("인증번호["+ auth.getAuthNum() +"]를 입력해 주세요");

        SmsSendResponse res = sendSms(req);
        alertAdmin(res);
    }
}
