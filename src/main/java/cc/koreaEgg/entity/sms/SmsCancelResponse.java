package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsCancelResponse {

    /*
    /*** 에러코드 ****
		-801 : 메세지ID 미입력
		-802 : 메세지ID 오류
		-803 : 예약대기중인 문자 없음
		-804 : 발송 5분전까지만 취소가능
		-805 : 전송완료로 취소불가
		-809 : 기타오류
    */
    private Integer result_code; //결과코드(API 수신유무)
    private String message; //	결과 메세지( result_code 가 0 보다 작은경우 실패사유 표기)
    private String cancel_date; //취소일자	YYYY-MM-DD HH:II:SS

}
