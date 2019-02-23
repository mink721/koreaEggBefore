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
  private String mobile;

  private String shopName;
  private String postNum;
  private String address;
  private String addressDetail;
  private String shopTel;
  /* TODO AN shop 이미지는 어떻게 저장함?*/
  private String profileImage;

  private double longitude;
  private double latitude;

  private Date regDate;
  private Date updateDate;
  private Date expireDate; /* TODO AN history에서 가져와야함 로그인할때마다 만료일 확인할거임*/

  //생년월일
  private Date birthday;
  //로그인횟수
  private Integer loginCount;
  private String homepage;

  private Role role;
  private String memo;
  private Integer status;


  public void setRole( String role ) {
    this.role = Role.valueOf(role);
  }

}
