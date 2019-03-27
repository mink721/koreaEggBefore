package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.SmsService;
import cc.koreaEgg.service.UserService;
import cc.koreaEgg.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SmsService smsService;

  @Autowired
  private ProductService productService;

  @PostAuthorize("isAuthenticated() and (( #model[user].id == principal.id ) or hasRole('ROLE_ADMIN'))")
  @RequestMapping(value = "/mypage", method = RequestMethod.GET)
  public String readUser(Model model, @AuthenticationPrincipal User user){
    model.addAttribute("user", userService.selectUserById(user.getId()));
    return  "user/mypage";
  }

  @GetMapping(value = "/user/register")
  public String registerUser(Model model){

    model.addAttribute("register",true);

    if( !model.containsAttribute("user")){
      model.addAttribute("user", new User());
    }
    if( !model.containsAttribute("shop")){
      model.addAttribute("shop", new User());
    }

    return "user/register";
  }

  @PostMapping(value = "/user/existId")
  @ResponseBody
  public boolean existId(String id){
      return userService.existId(id);
  }

  @PostMapping(value = "/user/register")
  public String createUser(@Valid @ModelAttribute("user")User user,
                           BindingResult result, Model model,
                           @Valid @ModelAttribute("shop")Shop shop,
                           BindingResult result2){

    if( LogUtils.bindingResult(result) ){
      return registerUser(model);
    }

    if( LogUtils.bindingResult(result2) ){
      return registerUser(model);
    }

    if(shop.getName() != null){
      userService.createShop(shop);
      user.setShopId(shop.getId());
    }

    userService.createUser(user);
    return  "redirect:" + "/login?register";
  }


  @GetMapping(value = "/user/password_change")
  public String changePwd(Model model, @AuthenticationPrincipal User user) {
    return "user/changePwd";
  }

  @ResponseBody
  @PostMapping(value = "/user/password_change")
  public boolean updatePwd(Model model, String pwd, String newPwd, @AuthenticationPrincipal User user) {

    if( userService.changePwd(user.getId(), pwd, newPwd) ){
      return true;
    }else {
      return false;
    }

  }

  @GetMapping(value = "/user/mod")
  public String modUser(Model model, @AuthenticationPrincipal User user) {
    model.addAttribute("user", userService.selectUserById(user.getId()));
    /*TODO shop select*/
    //model.addAttribute("shop", productService.selectSh());
    model.addAttribute("modify",true);
    return "user/mypage";
  }

  @PreAuthorize("isAuthenticated() and #user.id == principal.id")
  @PostMapping(value = "/user/mod")
  public String updateUser(@Valid @ModelAttribute("user")User user,
                           BindingResult result, Model model){

    if( LogUtils.bindingResult(result) ){
      model.addAttribute("modify",true);
      return "user/mypage";
    }

    userService.updateUser(user);
    return  "redirect:" + "/login?register";
  }

  /*TODO 더해야함*/
  @RequestMapping(value = "/recover-account", method = RequestMethod.GET)
  public String recoverUser(){
    return  "user/recover";
  }

  @ResponseBody
  @RequestMapping(value = "/auth", method = RequestMethod.GET)
  public String authRequest(@Valid @ModelAttribute("auth")AuthNum auth,
                            BindingResult result, Model model){

      if( LogUtils.bindingResult(result) ){
          log.debug(auth.getPhone());
          return "fail";
      }
    userService.requestAuth(auth.getPhone());

    return "sucess";
  }

  @ResponseBody
  @RequestMapping(value = "/authCheck", method = RequestMethod.GET)
  public boolean authCheck(@Valid @ModelAttribute("auth")AuthNum auth,
                          BindingResult result, Model model){

    if( LogUtils.bindingResult(result) ){
      log.debug(auth.getPhone());
      throw new DataIntegrityViolationException("phone number violation");
    }
    return userService.authCheck(auth);

  }

  @ResponseBody
  @RequestMapping(value = "/findId", method = RequestMethod.GET)
  public Map findIdByphone(@Valid @ModelAttribute("auth")AuthNum auth,
                           BindingResult result, Model model){

    if( LogUtils.bindingResult(result) ){
      log.debug(auth.getPhone());
      throw new DataIntegrityViolationException("phone number violation");
    }

    if ( userService.authCheck(auth) ){
      List<User> userList = userService.selectUserByPhone(auth.getPhone());
      Map ret = new HashMap();

      Iterator<User> itr = userList.iterator();

      while( itr.hasNext() )
      {
        User user = itr.next();
        ret.put(user.getUserId(), user.getRegDate());
      }

      return ret;
    }else{
      return null;
    }

  }

  @ResponseBody
  @RequestMapping(value = "/searhphone", method = RequestMethod.GET)
  public String searhphone(String id){

    User user = userService.loadUserEntityByUsername(id);
    if(user != null){
      /*TODO replace?*/
      return user.getPhone();
    }else{
      return null;
    }
  }

  /*TODO 더해야함*/
  @ResponseBody
  @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
  public String changePwd(String pwd, @Valid @ModelAttribute("auth")AuthNum auth,
                          BindingResult result, Model model){

    //TODO 인증은 어떻게?
    if( userService.authCheck(auth)){
      //User user = userService.selectphone
      return "sucess";
    }else{
      return "fail";
    }

  }

  /*TODO 더해야함*/
  @RequestMapping(value = "/class", method = RequestMethod.GET)
  public String upgrade(){
    return  "user/class";
  }


}
