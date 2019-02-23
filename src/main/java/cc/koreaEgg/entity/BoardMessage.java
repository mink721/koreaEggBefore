package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private Date regDate;
    private Date updateDate;

}
