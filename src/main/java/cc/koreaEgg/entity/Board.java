package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Board {

    public static int BOARD_NOTICE = 1;
    public static int BOARD_NEWS = 2;

    private Integer id;
    private Integer status; //CODE.ACTIVE , CODE.DELETE
    private String boardName;
    private Timestamp regDate;

}
