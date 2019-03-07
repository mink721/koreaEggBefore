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
import java.util.*;

/**
 * 관리자만 접근 가능한 컨트롤러
 * TODO 관리자 롤만 접근하도록 변경
 */
@Controller
@Slf4j
@RequestMapping(value = "/partner")
public class PartnerController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminHome() {
    return "partner/home";
    }


    /*  상품 관리 */
    @GetMapping(value = "/product/list")
    public String listProduct(@ModelAttribute("cri") Criteria cri, Model model) {
        //model.addAttribute("priceCastList", appService.selectPriceCast(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        //pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        return "partner/productList";
    }

    @GetMapping(value = "/product/register")
    public String registerProduct(){
        return  "partner/product";
    }

    @PostMapping(value = "/product/register")
    public String createProduct(Product goods){
        //appService.createBoardMessage(goods);
        return  "redirect:/partner/product/list";
    }

    @GetMapping(value = "/product/read")
    public String readProduct(long id, Model model) {
        //model.addAttribute("board", appService.selectBoardMessage(id));
        return "partner/product";
    }

    @GetMapping(value = "/product/mod")
    public String modProduct(long id, Model model) {
        //model.addAttribute("board", appService.selectBoardMessage(id));
        return "partner/product";
    }

    @PostMapping(value = "/product/mod")
    public String modProduct(Product goods){
        //appService.updateBoardMessage(board);
        return  "redirect:/partner/product/list";
    }

    @GetMapping(value = "/product/remove")
    public String removeProduct(long productId){
        //appService.deletePriceCast(castId);
        return  "redirect:/partner/product/list";
    }
    /* 상품 관리 끝*/

}
