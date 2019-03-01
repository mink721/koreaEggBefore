package cc.koreaEgg.controller;

import cc.koreaEgg.entity.Criteria;
import cc.koreaEgg.entity.PageMaker;
import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
  public String adminPriceInfo(@ModelAttribute("cri") Criteria cri, Integer areaId, Model model) {
    model.addAttribute("areaList", appService.selectAreaList());
    model.addAttribute("priceInfoList", appService.selectPriceInfoByAreaId(cri, areaId));

      PageMaker pageMaker = new PageMaker();
      pageMaker.setCri(cri);
      pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(cri, areaId));

      model.addAttribute("pageMaker", pageMaker);  // 게시판 하단의 페이징 관련, 이전페이지, 페이지 링크 , 다음 페이지
    return "admin/priceInfo";
  }

  @RequestMapping(value = "/createPriceInfo", method = RequestMethod.POST)
  public String createPriceInfo(PriceInfo info){
      appService.createPriceInfo(info);
      log.info("add PriceInfo "+info.toString());
      return  "redirect:" + "/admin/priceInfo";
  }

    @RequestMapping(value = "/updatePriceInfo", method = RequestMethod.POST)
    public String updatePriceInfo(PriceInfo info){
        appService.updatePriceInfo(info);
        log.info("update PriceInfo "+info.toString());
        return  "redirect:" + "/admin/priceInfo";
    }

    @RequestMapping(value = "/deletePriceInfo", method = RequestMethod.GET)
    public String deletePriceInfo(long infoId){
        appService.deletePriceInfo(infoId);
        log.info("delete PriceInfo "+infoId);
        return  "redirect:" + "/admin/priceInfo";
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
