package cc.koreaEgg.entity.sms;

public class SmsState {

    private Integer mdid;	//메세지ID	Integer
    private String type;	//문자구분(유형)	String
    private String sender;	//발신번호	String
    private String receiver;	//수신번호	String
    private String sms_state;	//전송상태	String
    private String reg_date;	//등록일	YYYY-MM-DD HH:ii:ss
    private String send_date;	//전송일	YYYY-MM-DD HH:ii:s
    private String reserve_date;	//예약일	YYYY-MM-DD HH:ii
}
