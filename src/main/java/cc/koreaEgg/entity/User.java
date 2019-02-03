package cc.koreaEgg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long idx;
  private String name;
  private String local;


  private String username;

  private String password;

  private String role;

}
