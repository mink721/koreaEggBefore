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
    private String typeName;
    private String title;
    private String contents;
    private Date regDate;

}
