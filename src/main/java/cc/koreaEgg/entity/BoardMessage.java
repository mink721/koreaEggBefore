package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Alias("boardMessage")
public class BoardMessage {

    private Long id;
    private Integer boardId;
    private Integer status;  //CODE.ACTIVE , CODE.DELETE
    private Long userId;
    private String boardName;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private Timestamp regDate;
    private Timestamp updateDate;

}
