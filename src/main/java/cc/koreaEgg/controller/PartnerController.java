package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    ProductService productService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminHome() {
    return "partner/home";
    }


    /*  상품 관리 */
    @GetMapping(value = "/product/list")
    public String listProduct(@ModelAttribute("cri") Criteria cri, Integer size, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("productList", productService.selectProductList(cri, null, user.getShopId(), size));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        //pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        return "partner/productList";
    }

    @GetMapping(value = "/product/register")
    public String registerProduct(Model model){
        model.addAttribute("product", new Product());
        return  "partner/product";
    }

    @PostMapping(value = "/product/register")
    public String createProduct(Product product){
        productService.createProduct(product);
        return  "redirect:/partner/product/list";
    }

    /* TODO role 조건??*/
    @GetMapping(value = "/product/read")
    public String readProduct(long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("product", productService.selectProduct(id, null));
        return "partner/product";
    }

    @GetMapping(value = "/product/mod")
    public String modProduct(long id, Model model) {
        model.addAttribute("board", productService.selectProduct(id, null));
        return "partner/product";
    }

    @PostMapping(value = "/product/mod")
    public String modProduct(Product goods){
        //productService.
        return  "redirect:/partner/product/list";
    }

    @GetMapping(value = "/product/remove")
    public String removeProduct(long productId){
        //appService.deletePriceCast(castId);
        return  "redirect:/partner/product/list";
    }
    /* 상품 관리 끝*/

}
