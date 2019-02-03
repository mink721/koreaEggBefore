package cc.koreaEgg.controller;

import cc.koreaEgg.dao.UserDetailsServiceDAO;
import cc.koreaEgg.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
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
  @RequestMapping(value = "/createUser", method = RequestMethod.GET)
  public String createUserView(Model model){
    model.addAttribute("user", new User());
    model.addAttribute("allProfiles",getProfiles());
    return "createUser";
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registUser", method = RequestMethod.POST)
  public String createUser(@Valid User user, BindingResult result, Model model){

    return "registUserSuccess";
  }

  private List<String> getProfiles() {
    List<String> list = new ArrayList<>();
    list.add("Developer");
    list.add("Manager");
    list.add("Director");
    return list;
  }


}
