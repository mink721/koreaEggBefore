package cc.koreaEgg.controller;

import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/user/register")
  public String registerUser(Model model){
    model.addAttribute("user", new User());
    return "user/register";
  }

  @PostMapping(value = "/user/existId")
  @ResponseBody
  public boolean existId(String id){
      return userService.existId(id);
  }

  @PostMapping(value = "/user/register")
  public String createUser(@Valid User user, BindingResult result, Model model){

    if(result.hasErrors()){
      log.info("유효하지 않는 입력값입니다");
      model.addAttribute("user", user);
      return "user/register";
    }
    userService.createUser(user);
    return  "redirect:" + "/login?register";
  }

  /*TODO 더해야함*/
  @RequestMapping(value = "/recover-account", method = RequestMethod.GET)
  public String recoverUser(){
    return  "recover";
  }


  /*TODO 더해야함*/
  @RequestMapping(value = "/findId", method = RequestMethod.POST)
  public String findIdByMobile(String mobile){
    //TODO 인증은 어떻게?
    return  "recover";
  }




}
