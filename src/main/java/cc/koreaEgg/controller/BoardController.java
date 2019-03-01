package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.entity.sms.SmsSendRequest;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private AppService appService;

    /* 공지사항 뉴스 리스트 */
    @RequestMapping(value = "/boardList", method = RequestMethod.GET)
    public String boardList(Model model) {
        List<BoardMessage> boardList = appService.selectBoardMessageList(null, 1, null, null);
        model.addAttribute("boardList", boardList);
        return "boardList";
    }

  @RequestMapping(value = "/contactUsList", method = RequestMethod.GET)
  public String contactUsList(Model model){

      //model.addAttribute("board", new Board());
     return  "contactUsList";
    }

    @PostMapping(value = "/registerBoard")
    public String registerBoardPost(@Valid BoardMessage board, BindingResult result, Model model){
        appService.createBoardMessage(board);
        log.info("Form submitted successfully");
      return  "redirect:" + "/boardList";
    }

    @PostMapping(value = "/registerContactUs")
    public String registerContactUsPost(@Valid ContactUs cu, BindingResult result) throws Exception{

      smsService.contacUsSms(cu);
      return  "redirect:" + "/contactUsList";
    }

    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Long id, Model model) {
        if(id == null){
            model.addAttribute("msg","조회된 게시글이 없습니다");
            model.addAttribute("board", new BoardMessage());
        }else{
            BoardMessage board = appService.selectBoardMessage(id);
            model.addAttribute("board", board);
        }
        return "board";
    }

  @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
  public String contactUs(Long id, Model model){
        //model.addAttribute("board", boardList);
        return  "contactUs";
  }


}
