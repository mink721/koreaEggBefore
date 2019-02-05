package cc.koreaEgg.entity;

import java.util.Date;

public class UserRoleHistory {

    private Long id;
    private Long userId;
    private int deposit;
    private Date updateDate;
    private Date expireDate;
    private Role afterRole;
    private Role beforeRole;

}
