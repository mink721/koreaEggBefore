package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsListRequest implements SmsRequest{

    private String sms_url = "https://apis.aligo.in/list/"; // 전송요청 URL
    private String key = "fjwn18a84pa0piidalyb8w4g6xrlsf7x";	        //인증용 API Key	O
    private String user_id = "mink721";	    //사용자id	O
    private Integer page = 1;	    //페이지번호	X(기본 1)	I
    private Integer page_size = 30;	//페이지당 출력갯수	X(기본 30) 30~500
    private String start_date = "";     //	조회시작일자	X(기본 최근일자)	YYYYMMDD
    private Integer limit_day = 30;	        //조회마감일자	X
    public Class getResponseClass(){
        return SmsListResponse.class;
    }

}
