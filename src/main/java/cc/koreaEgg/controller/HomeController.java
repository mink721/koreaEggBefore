package cc.koreaEgg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.singletonList;

@Controller
@Slf4j
public class HomeController {

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginPage() {
    return "login";
  }

  @RequestMapping(value = "/change/{role}/{name}", method = RequestMethod.GET)
  public String changeRole(@PathVariable("role") String changeRole, @PathVariable("name") String roleName, HttpServletRequest request) {

    Properties businessFunctions = new Properties();
    String resourceName = "role-to-bf.properties"; // could also be a constant
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
      businessFunctions.load(resourceStream);
    } catch (IOException ex) {
      log.error("role properties can not load", ex);
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

      return "home";
  }

}
