package cc.koreaEgg.dao;

import cc.koreaEgg.constants.Queries;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.mapper.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * CustomUserService
 */
@Component
public class UserDetailsServiceDAO implements UserDetailsService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private Mapper mapper;

  private static final String ROLE_PREFIX = "ROLE_";

  private Properties businessFunctions;



  /**
   *
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(final String username)
          throws UsernameNotFoundException {
    User user = loadUserEntityByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username + " not found");
    }

    businessFunctions = new Properties();
      String resourceName = "role-to-bf.properties"; // could also be a constant
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
        businessFunctions.load(resourceStream);
      } catch (IOException ex) {
        // handle error
      }

    Set<String> sourceSet =  StringUtils.commaDelimitedListToSet(businessFunctions.getProperty(user.getRole()));

    List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
    for(String userRole  : sourceSet){
      grantedAuthorityList.add(new SimpleGrantedAuthority(userRole));
    }

    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        //return singletonList(new SimpleGrantedAuthority(role));
        return grantedAuthorityList;
      }

      @Override
      public String getPassword() {
        return user.getPassword();
      }

      @Override
      public String getUsername() {
        return username;
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }
    };
  }

  public enum ROLE {
    ADMIN("ADMIN"), USER("USER");

    private String role;

    ROLE(String role) {
      this.role = role;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }

    @Override
    public String toString() {
      return ROLE_PREFIX + role;
    }

  }

  public void saveUser(User user) throws Exception {
    jdbcTemplate.update(Queries.INSERT_NEW_USER, user.getUsername(), user.getPassword(), ROLE.USER.toString());
  }

  public void deleteUser(String username) throws Exception {
    jdbcTemplate.update(Queries.DELETE_USER_BY_USERNAME, username);
  }

  public User loadUserEntityByUsername(String username) {
    List<User> users = jdbcTemplate.query(Queries.LOAD_USER_BY_USERNAME, mapper::mapUser, username);
    if (users == null || users.size() < 1) {
      return null;
    } else {
      return users.get(0);
    }
  }

  public List<User> loadAllUsers() throws Exception {
    return jdbcTemplate.query(Queries.LOAD_ALL_USERS, mapper::mapUser);
  }

  public void updateUser(User user) throws Exception {
    jdbcTemplate.update(Queries.UPDATE_USER_BY_USERNAME, user.getPassword(), user.getRole(), user.getUsername());
  }

}
