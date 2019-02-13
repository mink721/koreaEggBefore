package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
public class SmsStateRequest implements SmsRequest{

    private String sms_url = "https://apis.aligo.in/sms_list/"; // 전송요청 URL
    private String key = "fjwn18a84pa0piidalyb8w4g6xrlsf7x";	        //인증용 API Key	O
    private String user_id = "mink721";	    //사용자id	O
    private Integer mid;	//메세지 고유ID	O
    private Integer page = 1;	//페이지번호	X(기본 1)
    private Integer page_size = 30;	//페이지당 출력갯수	X(기본 30) 30~500\
    public Class getResponseClass(){
        return SmsStateResponse.class;
    }


}
