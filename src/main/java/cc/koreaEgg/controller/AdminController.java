package cc.koreaEgg.controller;

import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.AppService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

  @Autowired
  private UserService userService;
  @Autowired
  private AppService appService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String adminHome() {
    return "admin/home";
  }

  @RequestMapping(value = "/userList", method = RequestMethod.GET)
  public String adminUserList(@RequestParam Map<String,String> search, Model model) {
    List<User> userList = userService.selectAllUser(search.get("userId"),
            search.get("userName"),search.get("mobile"),search.get("shopName"),search.get("address"), search.get("role"));
    model.addAttribute("userList", userList);
    return "admin/userList";
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public String adminUser(long id, Model model) {
    User user = userService.selectUserById(id);
    model.addAttribute("user", user);
    return "admin/user";
  }

  @RequestMapping(value = "/priceInfo", method = RequestMethod.GET)
  public String adminPriceInfo(Model model) {
    List<PriceInfo> priceInfoList = appService.selectAllPriceInfo();
    model.addAttribute("priceInfoList", priceInfoList);
    return "admin/priceInfo";
  }

  @RequestMapping(value = "/priceCast", method = RequestMethod.GET)
  public String adminPriceCast(Model model) {
    return "admin/priceCast";
  }

  @RequestMapping(value = "/deposit", method = RequestMethod.GET)
  public String adminDeposit(Model model) {
    return "admin/deposit";
  }

  @RequestMapping(value = "/vs", method = RequestMethod.GET)
  public String adminVs(Model model) {
    return "admin/vs";
  }

  @RequestMapping(value = "/board", method = RequestMethod.GET)
  public String adminBoard(Model model) {
    return "admin/board";
  }

  @RequestMapping(value = "/product", method = RequestMethod.GET)
  public String adminProduct(Model model) {
    return "admin/product";
  }

}
