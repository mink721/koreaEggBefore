package cc.koreaEgg.entity.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class SmsStateResponse {

    private Integer result_code; //결과코드(API 수신유무)
    private String message; //	결과 메세지( result_code 가 0 보다 작은경우 실패사유 표기)
    private String next_yn; //다음조회목록 유무	String
    private ArrayList<SmsState> list; //목록 배열	Array

}
