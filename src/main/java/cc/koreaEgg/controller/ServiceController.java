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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class ServiceController {

    @Autowired
    private AppService appService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/priceInfo/list")
    public String listPriceInfo(Model model){
        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("list", appService.selectAllPriceInfo());
        return  "price/infoList";
    }

    @GetMapping(value = "/priceInfo/read")
    public String readPriceInfo(@ModelAttribute("cri") Criteria cri, Integer id, Model model){
        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("list", appService.selectPriceInfoByAreaId(cri, id));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(id));

        model.addAttribute("pageMaker", pageMaker);

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
    public String listAgent(@ModelAttribute("cri") Criteria cri, Double lon, Double lat, Model model, String addr){

        if( lon == null && lat == null){
            lon = 37.256714720260405;
            lat = 127.03035456510496;
        }
        model.addAttribute("agentList", productService.selectShopList(cri, lon, lat, Role.AGENT.name()) );

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        /*TODO 카운트*/
        pageMaker.setTotalCount(10);

        model.addAttribute("pageMaker", pageMaker);

        //model.addAttribute("product", new Product());
        return  "shop/agentList";
    }

    @RequestMapping(value = "/agent/read", method = RequestMethod.GET)
    public String listAgent(long id){


        //model.addAttribute("product", new Product());
        return  "shop/agent";
    }

}
