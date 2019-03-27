package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.entity.sms.SmsSendRequest;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.SmsService;
import cc.koreaEgg.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private AppService appService;

    /* 공지사항 리스트 */
    @GetMapping(value = "/board/list")
    public String listBoard(@ModelAttribute("cri") Criteria cri, Model model) {
        model.addAttribute("boardList", appService.selectBoardMessageList(null, 1, null, cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectBoardMessageListCount(null, 1, null));
        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList();
        model.addAttribute("search", search);

        return "board/boardList";
    }

    @GetMapping(value = "/board/read")
    public String readBoard(long id, Model model) {
        model.addAttribute("board", appService.selectBoardMessage(id));
        return "board/board";
    }

    @RequestMapping(value = "/contact/list", method = RequestMethod.GET)
    public String contactUsList(@ModelAttribute("cri") Criteria cri, @AuthenticationPrincipal User user, Model model){

        model.addAttribute("contactList", appService.selectContactUsList(user.getId(), null, cri ));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountContactUs(user.getId(), null));
        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList();
        model.addAttribute("search", search);

        return  "board/contactList";
    }

    @GetMapping(value = "/contact/register")
    public String registerContact(Model model){
        if( !model.containsAttribute("contact")){
            model.addAttribute("contact", new ContactUs());
        }
        model.addAttribute("register",true);
        return  "board/contactUs";
    }
    @PostMapping(value = "/contact/register")
    public String createContact(@Valid  @ModelAttribute("contact")ContactUs contact, BindingResult result, @AuthenticationPrincipal User user, Model model){

        if( LogUtils.bindingResult(result) ){
            return registerContact(model);
        }

        contact.setUserId(user.getId());
        appService.createContactUs(contact);
        return  "redirect:/contact/list";
    }

    /*TODO POST 잘되는지 확인*/
    @PostAuthorize("isAuthenticated() and (( #model[contact].userId == principal.id ) or hasRole('ROLE_ADMIN'))")
    @GetMapping(value = "/contact/read")
    public String contactUs(long id, Model model){
        model.addAttribute("contact", appService.selectContactUs(id));
        return  "board/contactUs";
    }

    @GetMapping(value = "/contact/remove")
    public String removeContact(long id, @AuthenticationPrincipal User user){
        ContactUs cu = appService.selectContactUs(id);
        if(cu.getUserId() == user.getId()){
            appService.deleteContactUs(id);
        }
        return  "redirect:/contact/list";
    }

}
