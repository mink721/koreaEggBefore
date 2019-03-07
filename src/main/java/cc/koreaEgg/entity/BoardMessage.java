package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Alias("boardMessage")
public class BoardMessage {

    private Long id;
    @NotNull
    private Integer boardId;
    private Integer status;
    private Long userId;
    private String boardName;
    @NotNull
    private String title;
    private String contents;
    private Timestamp regDate;
    private Timestamp updateDate;

}
