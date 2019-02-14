package cc.koreaEgg.controller;

import cc.koreaEgg.entity.Board;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@Slf4j
public class ServiceController {

  @RequestMapping(value = "/board", method = RequestMethod.GET)
  public String boardView(Model model){
    model.addAttribute("board", new Board());
    return  "board";
  }

  @RequestMapping(value = "/registBoard", method = RequestMethod.GET)
  public String createBoardView(Model model){
    return  "redirect:" + "/board?regist";
  }

}
