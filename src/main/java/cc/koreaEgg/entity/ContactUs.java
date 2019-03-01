package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;


@Getter
@Setter
@ToString
@Alias("contactUs")
public class ContactUs {

    private Long id;
    private Long userId;
    private Boolean answerFlag;

    private String title;
    private String reqName;
    private String reqMobile;
    private String contents;
    private Date regDate;

    private String answer;
    private Date answerDate;
}
