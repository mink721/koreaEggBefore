package cc.koreaEgg.controller;

import cc.koreaEgg.entity.Board;
import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.entity.Product;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class ServiceController {

    @Autowired
    private AppService appService;

 @RequestMapping(value = "/product", method = RequestMethod.GET)
  public String productView(Model model){
    model.addAttribute("product", new Product());
    return  "product";
  }

  @RequestMapping(value = "/registProduct", method = RequestMethod.GET)
  public String createProductView(Model model){
    return  "redirect:" + "/product?regist";
  }

    @RequestMapping(value = "/priceInfo", method = RequestMethod.GET)
    public String priceInfo(Model model){
        List<PriceInfo> list = appService.selectAllPriceInfo();
        model.addAttribute("list",list);
        log.info("list info " + list.toString());
        return  "priceInfo";
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
