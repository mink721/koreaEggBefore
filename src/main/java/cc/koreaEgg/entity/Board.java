package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {

    private Long id;
    private String type;
    private String title;
    private String contents;
    private String witer;
    private int count;
    private Long parentId;

}
