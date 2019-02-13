package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class SmsRemainRequest implements SmsRequest{

    private String sms_url = "https://apis.aligo.in/remain/"; // 전송요청 URL
    private String key = "fjwn18a84pa0piidalyb8w4g6xrlsf7x";	        //인증용 API Key	O
    private String user_id = "mink721";	    //사용자id	O
    public Class getResponseClass(){
        return HashMap.class;
    }
}
