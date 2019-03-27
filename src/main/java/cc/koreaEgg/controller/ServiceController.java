package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ServiceController {

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/")
    public String homepage(@ModelAttribute("cri") Criteria cri, Double lon, Double lat, String addr, Integer size,
                            Model model, @AuthenticationPrincipal User user) {

        Role role = Role.USER;
        if(user != null){
            role = user.getRole();
        }

        model.addAttribute("productList", productService.selectProductList(cri, role,null, size ));


        if( lon == null && lat == null){
            lon = 37.256714720260405;
            lat = 127.03035456510496;
        }
        model.addAttribute("agentList", userService.selectShopList(cri, lon, lat, Role.AGENT.name()) );
        model.addAttribute("partnerList", userService.selectShopList(cri, lon, lat, Role.PARTNER.name()) );

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        /*TODO 카운트*/
        pageMaker.setTotalCount(10);

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("lon", "lat" ,"addr" ,"size");
        model.addAttribute("lon", lon);
        model.addAttribute("lat", lat);
        model.addAttribute("addr", addr);
        model.addAttribute("size", size);

        return "home";
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/sendSMS", method = RequestMethod.GET)
    public String sendSMS() {
        return "admin/sendSMS";
    }


    @GetMapping(value = "/priceInfo/list")
    public String listPriceInfo(Model model){
        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("list", appService.selectAllPriceInfo());
        return  "price/infoList";
    }

    @GetMapping(value = "/priceInfo/read")
    public String readPriceInfo(@ModelAttribute("cri") Criteria cri, Integer id,  Model model){

        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("list", appService.selectPriceInfoByAreaId(cri, id));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(id));

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("id");
        model.addAttribute("search", search);
        model.addAttribute("id", id);

        return  "price/info";
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public String productView(long id, Model model, @AuthenticationPrincipal User user){
        Role role = Role.USER;
        if(user != null){
            role = user.getRole();
        }
    model.addAttribute("product", productService.selectProduct(id, role));
    return  "product/product";
    }

    @RequestMapping(value = "/agent/list", method = RequestMethod.GET)
    public String listAgent(@ModelAttribute("cri") Criteria cri,  Double lon, Double lat, String addr, Model model){

        if( lon == null && lat == null){
            lon = 37.256714720260405;
            lat = 127.03035456510496;
        }
        model.addAttribute("agentList", userService.selectShopList(cri, lon, lat, Role.AGENT.name()) );

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        /*TODO 카운트*/
        pageMaker.setTotalCount(10);
        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("lon", "lat" , "addr");
        model.addAttribute("search", search);
        model.addAttribute("lon", lon);
        model.addAttribute("lat", lat);
        model.addAttribute("addr", addr);

        return  "shop/agentList";
    }

    /*TODO 이걸해?*/
    @RequestMapping(value = "/agent/read", method = RequestMethod.GET)
    public String listAgent(long id){

        //model.addAttribute("product", new Product());
        return  "shop/agent";
    }

}
