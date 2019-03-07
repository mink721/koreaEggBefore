package cc.koreaEgg.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("user")
public class User implements UserDetails {

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

  private Timestamp regDate;
  private Timestamp updateDate;
  private Timestamp expireDate; /* TODO AN history에서 가져와야함 로그인할때마다 만료일 확인할거임*/

  //생년월일
  private Timestamp birthday;
  //로그인횟수
  private Integer loginCount;
  private String homepage;

  private Role role;
  private String memo;
  private Integer status;

  List<GrantedAuthority> authorities;
  private boolean accountNonExpired = true;
  private boolean accountNonLocked = true;
  private boolean credentialsNonExpired = true;
  private boolean enabled = true;

  public void setRole( String role ) {
    this.role = Role.valueOf(role);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return pwd;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
