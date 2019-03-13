package cc.koreaEgg.controller;

import cc.koreaEgg.entity.Criteria;
import cc.koreaEgg.entity.PageMaker;
import cc.koreaEgg.entity.Role;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.singletonList;

@Controller
@Slf4j
public class HomeController {

  @Autowired
  ProductService productService;


  @RequestMapping(value = "/")
  public String homepage(@ModelAttribute("cri") Criteria cri, Double lon, Double lat, Model model, String addr, Integer size) {

    model.addAttribute("productList", productService.selectProductList(cri, null, size ));


    if( lon == null && lat == null){
      lon = 37.256714720260405;
      lat = 127.03035456510496;
    }
    model.addAttribute("agentList", productService.selectShopList(cri, lon, lat, Role.AGENT.name()) );
    model.addAttribute("partnerList", productService.selectShopList(cri, lon, lat, Role.PARTNER.name()) );

    PageMaker pageMaker = new PageMaker();
    pageMaker.setCri(cri);
    /*TODO 카운트*/
    pageMaker.setTotalCount(10);

    model.addAttribute("pageMaker", pageMaker);
    return "home";
  }

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

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Authentication newAuth = new UsernamePasswordAuthenticationToken(roleName, auth.getCredentials(), grantedAuthorityList);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
    log.trace("change role",grantedAuthorityList.toString());

      return "home";
  }

  }
