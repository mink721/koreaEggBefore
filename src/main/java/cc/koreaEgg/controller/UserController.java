package cc.koreaEgg.controller;

import cc.koreaEgg.dao.UserDetailsServiceDAO;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class UserController {

  @Autowired
  private UserDetailsServiceDAO userDetailsServiceDAO;

  @Autowired
  private UserService userService;

  //@PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registUser", method = RequestMethod.GET)
  public String createUserView(Model model){
    model.addAttribute("user", new User());
    return "regist";
  }

  //@PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registUser", method = RequestMethod.POST)
  public String createUser(@Valid User user, BindingResult result, Model model){

    if(result.hasErrors()){
      log.info("Validation errors while submitting form.");
      model.addAttribute("user", user);
      return "regist";
    }
    userService.addUser(user);
    model.addAttribute("allUsers",userService.getAllUser());
    log.info("Form submitted successfully");
    return  "redirect:" + "/registUser?success";
  }


}
