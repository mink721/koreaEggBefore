package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Board {

    private Long id;
    private String type;
    private String title;
    private String contents;
    private String witer;
    private Date regDate;
    private int count;
    private Long parentId;

}
