package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sms {

    private Integer mid;	//메세지ID	Integer
    private String type;	//문자구분(유형)	String
    private String sender;	//발신번호	String
    private Integer sms_count;	//전송요청수	Integer
    private String reserve_state;	//요청상태	String
    private String msg;	//메세지 내용	String
    private Integer fail_count;	//처리실패건수	Integer
    private String reg_date;	//등록일	YYYY-MM-DD HH:ii:ss
    private String reserve;	//예약일자	YYYY-MM-DD HH:ii:ss
}
