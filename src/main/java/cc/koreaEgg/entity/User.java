package cc.koreaEgg.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@Alias("user")
public class User {

  private Long id;
  private String userId;
  private String pwd;
  private String userName;
  private String tel;

  private String shopName;
  private String postNum;
  private String address;
  private String roadAddress;
  private String detailAddress;
  private String shopTel;

  private int logitude;
  private int latitude;

  private Date regDate;
  private Date updateDate;
  private String memo;

  private Boolean status;
  private Role role;

  public void setRole( String role ) {
    this.role = Role.valueOf(role);
  }

}
