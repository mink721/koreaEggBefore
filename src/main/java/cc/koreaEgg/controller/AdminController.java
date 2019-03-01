package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 관리자만 접근 가능한 컨트롤러
 * TODO 관리자 롤만 접근하도록 변경
 */
@Controller
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminHome() {
    return "admin/home";
    }


    /*  유저관리  */
    @GetMapping(value = "/user/list")
    public String listUser(@ModelAttribute("cri") Criteria cri,
                              @RequestParam Map<String,String> search,
                              Model model) {
    List<User> userList = userService.selectAllUser(cri, search.get("userId"),
            search.get("userName"),search.get("mobile"),search.get("shopName"),search.get("address"), search.get("role"));
    model.addAttribute("userList", userList);

     PageMaker pageMaker = new PageMaker();
     pageMaker.setCri(cri);
     pageMaker.setTotalCount(userService.selectCountAllUser(search.get("userId"),
              search.get("userName"),search.get("mobile"),search.get("shopName"),search.get("address"), search.get("role")));

     model.addAttribute("pageMaker", pageMaker);

    return "admin/user/userList";
    }

    @GetMapping(value = "/user/read")
    public String readUser(long id, Model model) {
    model.addAttribute("user", userService.selectUserById(id));
    return "admin/user";
    }

    @PostMapping(value = "/user/mod")
    public String modUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            log.info("유효하지 않는 입력값입니다");
            model.addAttribute("user", user);
            return "admin/user";
        }
        userService.updateUser(user);
        return "redirect:/admin/user/list";
    }

    @PostMapping(value = "/user/remove")
    public String removeUser(long id, Model model) {
        userService.deleteUser(id);
        return  "redirect:/admin/user/list";
    }
    /*  유저관리 끝  */


    /*  난가정보 관리 */
    @GetMapping(value = "/priceInfo/list")
    public String listPriceInfo(@ModelAttribute("cri") Criteria cri, Integer areaId, Model model) {
        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("priceInfoList", appService.selectPriceInfoByAreaId(cri, areaId));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(cri, areaId));

        model.addAttribute("pageMaker", pageMaker);
    return "admin/priceInfo/infoList";
    }

    @PostMapping(value = "/priceInfo/register")
    public String createPriceInfo(PriceInfo info){
      appService.createPriceInfo(info);
      return  "redirect:/admin/priceInfo/list";
    }

    @PostMapping(value = "/priceInfo/mod")
    public String modPriceInfo(PriceInfo info){
        appService.updatePriceInfo(info);
        return  "redirect:/admin/priceInfo/list";
    }

    @GetMapping(value = "/priceInfo/remove")
    public String removePriceInfo(long infoId){
        appService.deletePriceInfo(infoId);
        return  "redirect:/admin/priceInfo/list";
    }
    /* 난가정보 관리 끝*/

    /*  난가정보 관리 */
    @GetMapping(value = "/priceCast/list")
    public String listPriceCast(@ModelAttribute("cri") Criteria cri, Integer areaId, Model model) {
        model.addAttribute("priceCastList", appService.selectPriceCast(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        return "admin/priceCast/castList";
    }

    @PostMapping(value = "/priceCast/register")
    public String createPriceCast(PriceCast cast){
        appService.createPriceCast(cast);
        return  "redirect:/admin/priceCast/list";
    }

    @PostMapping(value = "/priceCast/mod")
    public String modPriceCast(PriceCast cast){
        appService.updatePriceCast(cast);
        return  "redirect:/admin/priceCast/list";
    }

    @GetMapping(value = "/priceCast/remove")
    public String removePriceCast(long castId){
        appService.deletePriceCast(castId);
        return  "redirect:/admin/priceCast/list";
    }
    /* 난가예보 관리 끝*/

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
