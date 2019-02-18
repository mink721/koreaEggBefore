package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class ContactUs {

    private Long id;
    private Long userId;
    private String reqName;
    private String reqMobile;
    private String title;
    private String contents;
    private String answer;
    private Date regDate;
}
