package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
public class SmsCancelRequest implements SmsRequest {

    private String sms_url = "https://apis.aligo.in/cancel/";
    private String key = "fjwn18a84pa0piidalyb8w4g6xrlsf7x";	        //인증용 API Key	O
    private String user_id = "mink721";	    //사용자id	O
    private Integer mid;	    //메세지ID	O
    public Class getResponseClass(){
        return SmsCancelResponse.class;
    }

}
