package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.UserService;
import cc.koreaEgg.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 파트너만 접근 가능한 컨트롤러
 * TODO 파트너 관리자 롤만 접근하도록 변경
 */
@Controller
@Slf4j
@RequestMapping(value = "/partner")
public class PartnerController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String partnerHome() {
    return "partner/home";
    }

    /*  상품 관리 */
    @GetMapping(value = "/product/list")
    public String listProduct(@ModelAttribute("cri") Criteria cri, Integer size, @AuthenticationPrincipal User user, Model model) {


        model.addAttribute("productList", productService.selectProductList(cri, null, user.getShopId(), size));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        /*TODO 카운트*/
        //pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("size");
        model.addAttribute("search", search);
        model.addAttribute("size", size);

        return "partner/productList";
    }

    @GetMapping(value = "/product/register")
    public String registerProduct(Model model){

        if( !model.containsAttribute("product")){
            model.addAttribute("product", new Product());
        }
        model.addAttribute("register",true);

        return  "partner/product";
    }

    @PostMapping(value = "/product/register")
    public String createProduct(@Valid @ModelAttribute("product")Product product,
                                BindingResult result, Model model){

        if( LogUtils.bindingResult(result) ){
            return registerProduct(model);
        }

        productService.createProduct(product);
        return  "redirect:/partner/product/list";
    }

    @PostAuthorize( "#model[product].shopId == #user.shopId" )
    @GetMapping(value = "/product/read")
    public String readProduct(long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("product", productService.selectProduct(id, null));
        return "partner/product";
    }

    @PostAuthorize( "#model[product].shopId == #user.shopId" )
    @GetMapping(value = "/product/mod")
    public String modProduct(long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("product", productService.selectProduct(id, null));
        model.addAttribute("modify",true);
        return "partner/product";
    }

    @PostAuthorize( "#model[product].shopId == #user.shopId" )
    @PostMapping(value = "/product/mod")
    public String modProduct(@Valid @ModelAttribute("board")Product product,
                             BindingResult result, Model model,
                             @AuthenticationPrincipal User user){

        if( LogUtils.bindingResult(result) ){
            return registerProduct(model);
        }
        /* TODO 업데이트 */
        //productService.
        return  "redirect:/partner/product/list";
    }

    @GetMapping(value = "/product/remove")
    public String removeProduct(long id, @AuthenticationPrincipal User user){
        Product product = productService.selectProduct(id, null);
        if(product.getShopId() == user.getShopId()){
            /*TODO 삭제*/
        }

        return  "redirect:/partner/product/list";
    }
    /* 상품 관리 끝*/

}
