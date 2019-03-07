package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@ToString
@Alias("uploadFile")
public class UploadFile {

    private Long id;

    private Integer targetType;

    private Long targetId;
    // 상태
    private int status;

    private Long userId;
    // 저장 경로
    private String path;
    // 원본파일명
    private String name;
    // 파일확장자
    private String fileExtension;
    // 등록일시
    private Date regDate;
    // 수정일시
    private Date updateDate;



}
