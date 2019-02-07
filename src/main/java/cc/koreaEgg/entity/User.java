package cc.koreaEgg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Alias("user")
public class User {

  private Long id;
  private String userId;
  private String password;
  private Role role;
  private String rolet;

  private Boolean status;
  private String tel;
  private String address;
  private Date updateDate;
  private Date createDate;

  private String shopName;
  private String shopTel;
  private String roadAddress;
  private String memo;
  @NotNull
  private String username;

}
