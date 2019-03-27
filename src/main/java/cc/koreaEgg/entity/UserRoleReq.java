package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
@Getter
@Setter
@ToString
@Alias("userRoleReq")
public class UserRoleReq {

    private Long id;
    private Long userId;
    private String userIdId;
    private String userName;
    private Integer status;
    private Role role;
    private String reqName; // 입금자명
    private Integer deposit;    // 입금
    private String memo;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp expireDate;
    private Integer renewalCount; //갱신횟수


}
