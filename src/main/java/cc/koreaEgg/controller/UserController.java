package cc.koreaEgg.controller;

import cc.koreaEgg.dao.UserDetailsServiceDAO;
import cc.koreaEgg.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.singletonList;

@Controller
public class UserController {

  @Autowired
  private UserDetailsServiceDAO userDetailsServiceDAO;

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String registration(User newUser) {
    try {
      if (userDetailsServiceDAO.loadUserEntityByUsername(newUser.getUsername()) != null) {
        return "redirect:" + "/login?registration&error";
      } else {
        userDetailsServiceDAO.saveUser(newUser);
        return "redirect:" + "/login?registration&success";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:" + "/login?registration&errorServer";
    }
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginPage() {
    return "login";
  }

  /*
   *@PostAuthorize("isAuthenticated() and returnObject.user_id == principal.username") https://syaku.tistory.com/292
   * */

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String indexPage() {
    System.out.println("Home Page");
    return "main/index";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String retailPage() {
    return "main/index";
  }

  @RequestMapping(value = "/cast", method = RequestMethod.GET)
  public String castPage() {
    return "main/cast";
  }

  @RequestMapping(value = "/board", method = RequestMethod.GET)
  public String boardPage() {
    return "main/board";
  }

  @RequestMapping(value = "/product", method = RequestMethod.GET)
  public String productPage() {
    return "main/product";
  }

  @RequestMapping(value = "/agent", method = RequestMethod.GET)
  public String agentPage() { return "main/agent"; }

  @RequestMapping(value = "/agentList", method = RequestMethod.GET)
  public String agentListPage() { return "main/agentList"; }

  @RequestMapping(value = "/class", method = RequestMethod.GET)
  public String classPage() { return "main/class"; }

  @RequestMapping(value = "/change/{role}/{name}", method = RequestMethod.GET)
  public String changeRole(@PathVariable("role") String changeRole, @PathVariable("name") String roleName, HttpServletRequest request) {

    Properties businessFunctions = new Properties();
    String resourceName = "role-to-bf.properties"; // could also be a constant
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
      businessFunctions.load(resourceStream);
    } catch (IOException ex) {
      // handle error
    }

      Set<String> sourceSet =  StringUtils.commaDelimitedListToSet(businessFunctions.getProperty(changeRole));
      List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
      for(String userRole  : sourceSet){
          grantedAuthorityList.add(new SimpleGrantedAuthority(userRole));
      }

    String role = businessFunctions.getProperty(changeRole);
    Collection<? extends GrantedAuthority> roleList = singletonList(new SimpleGrantedAuthority(role));
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Authentication newAuth = new UsernamePasswordAuthenticationToken(roleName, auth.getCredentials(), grantedAuthorityList);
    SecurityContextHolder.getContext().setAuthentication(newAuth);

      return "main/index";
  }

}
