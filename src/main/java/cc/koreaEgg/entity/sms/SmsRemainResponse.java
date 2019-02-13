package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
public class SmsRemainResponse {

    private Integer result_code; //결과코드(API 수신유무)
    private String message; //	결과 메세지( result_code 가 0 보다 작은경우 실패사유 표기)
    private Integer SMS_CNT;	//단문전송시 발송가능한건수	Integer
    private Integer LMS_CNT;	//단문전송시 발송가능한건수	Integer
    private Integer MMS_CNT;	//그림(사진)전송시 발송가능한건수	Integer

}
