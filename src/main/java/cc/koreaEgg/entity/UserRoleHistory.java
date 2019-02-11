package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;
@Getter
@Setter
@ToString
@Alias("userRoleHistory")
public class UserRoleHistory {

    private Long id;
    private Long userId;
    private int deposit;
    private Date updateDate;
    private Date expireDate;
    private Role afterRole;
    private Role beforeRole;

}
