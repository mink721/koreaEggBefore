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
    private String answerName;
    private String answerMobile;
    private String title;
    private String contents;
    private Date regDate;
}
