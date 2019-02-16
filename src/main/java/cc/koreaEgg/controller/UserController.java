package cc.koreaEgg.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registUser", method = RequestMethod.GET)
  public String createUserView(Model model){
    model.addAttribute("user", new User());
    return "regist";
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/existId", method = RequestMethod.POST)
  @ResponseBody
  public boolean existId(String id){
      boolean ret = userService.existId(id);
      log.info("existId ", ret);
      return ret;
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/registUser", method = RequestMethod.POST)
  public String createUser(@Valid User user, BindingResult result, Model model){

    if(result.hasErrors()){
      log.info("Validation errors while submitting form.");
      model.addAttribute("user", user);
      return "regist";
    }
    userService.addUser(user);
    log.info("addUser "+user.toString());
    log.info("Form submitted successfully");
    return  "redirect:" + "/login?afterRegist";
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/recover-account", method = RequestMethod.GET)
  public String recoverUser(){
    return  "recover";
  }



  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/findId", method = RequestMethod.POST)
  public String findIdByMobile(String mobile){
    //TODO 인증은 어떻게?
    return  "recover";
  }




}
