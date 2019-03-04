package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String productView(Model model){
    model.addAttribute("product", new Product());
    return  "product";
    }

    @RequestMapping(value = "/registProduct", method = RequestMethod.GET)
    public String createProductView(Model model){
    return  "redirect:" + "/product?regist";
    }



    @RequestMapping(value = "/registPriceInfo", method = RequestMethod.GET)
    public String registPriceInfoGet(){
        return  "registPriceInfo";
    }


    @RequestMapping(value = "/registPriceInfo", method = RequestMethod.POST)
    public String registPriceInfoPost(@Valid PriceInfo info, BindingResult result, Model model){

        model.addAttribute("info", info);
        if(result.hasErrors()){
            log.info("Validation errors while submitting form.");
            return "registPriceInfo";
        }
        appService.createPriceInfo(info);
        log.info("create price Info success" + info.toString());
        return  "redirect:" + "/sendSMS";
    }

    @RequestMapping(value = "/sendSMS", method = RequestMethod.GET)
    public String sendSMS(Model model){
        return  "sendSMS";
    }
}
