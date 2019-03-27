package cc.koreaEgg.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

  private Role baseRole;
  private Role role;
  private boolean roleChange;

  private Integer status; //CODE.ACTIVE CODE.DELETE
  @Size(min=1, max=20)
  private String userId;
  private String pwd;
  @NotBlank
  private String userName;
  @Pattern(regexp = "^01\\d-?\\d{3,4}-?\\d{4}", message = "010-0000-0000 형식으로 입력해주세요")
  private String phone;
  private String memo;
  private Timestamp birthday;

  private Long shopId;
  private Shop shop;

  private Timestamp regDate;
  private Timestamp updateDate;
  private Timestamp expireDate;

  private Integer loginCount;
  private Timestamp loginDate;

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
