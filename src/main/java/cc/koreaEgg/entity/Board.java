package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Board {

    public static int BOARD_NOTICE = 1;
    public static int BOARD_NEWS = 2;

    private Integer id;
    private Integer status;
    private String boardName;
    private Date regDate;

}
