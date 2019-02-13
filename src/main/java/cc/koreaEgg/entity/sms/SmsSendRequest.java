package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsSendRequest {

    /*
    * sms.put("msg", "%고객명%님. 안녕하세요. API TEST SEND"); // 메세지 내용
            sms.put("receiver", "01111111111,01111111112"); // 수신번호
            sms.put("destination", "01111111111|담당자,01111111112|홍길동"); // 수신인 %고객명% 치환
            sms.put("sender", ""); // 발신번호
            sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
            sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
            sms.put("testmode_yn", "Y"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
            sms.put("title", "제목입력); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)

            String image = "";
            //image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치

    * */

    private String sms_url = "https://apis.aligo.in/send/";	        //인증용 API Key	O
    private String key = "fjwn18a84pa0piidalyb8w4g6xrlsf7x";	        //인증용 API Key	O
    private String user_id = "mink721";	    //사용자id	O
    private String sender = "01080790179";	    //발신자 전화번호 (최대 16bytes)	O
    private String receiver ="";	//수신자 전화번호 - 컴마(,)분기 입력으로 최대 1천명	O
    private String msg_type="";     //	SMS(단문) , LMS(장문), MMS(그림문자) 구분	O
    private String msg="";	        //메시지 내용	O	 (1~2,000Byte)
    private String title="";	    //문자제목(LMS,MMS만 허용)	X	 (1~44Byte)
    private String destination="";	//%고객명% 치환용 입력	X
    private String rdate="";	    //예약일 (현재일이상)	X	YYYYMMDD
    private String rtime="";	    //예약시간 - 현재시간기준 10분이후	X	HHII
    private String testmode_yn="Y";	//연동테스트시 Y 적용	X	String
    private String imagePath="";

}
