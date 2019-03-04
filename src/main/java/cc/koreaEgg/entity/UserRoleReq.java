package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;
@Getter
@Setter
@ToString
@Alias("userRoleReq")
public class UserRoleReq {

    private Long id;
    private Long userId;
    private Long userName;
    private Integer status;
    private Role role;
    private String reqName; // 입금자명
    private int deposit;    // 입금
    private String memo;
    private Date regDate;
    private Date updateDate;
    private Date expireDate;
    private int renewalCount; //갱신횟수


}
