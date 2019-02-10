package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class UserRoleHistory {

    private Long id;
    private Long userId;
    private int deposit;
    private Date updateDate;
    private Date expireDate;
    private Role afterRole;
    private Role beforeRole;

}
