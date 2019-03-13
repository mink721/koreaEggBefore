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
import java.sql.Timestamp;
import java.util.*;

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


    /*  유저 관리  */
    @GetMapping(value = "/user/list")
    public String listUser(@ModelAttribute("cri") Criteria cri,
                              @RequestParam Map<String,String> search,
                              Model model) {
        userService.refreshRole();
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
    return "admin/user/user";
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
    /*  유저 관리 끝  */


    /*  난가 정보 관리 */
    @GetMapping(value = "/priceInfo/list")
    public String listPriceInfo(@ModelAttribute("cri") Criteria cri, Integer areaId, Model model) {
        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("priceInfoList", appService.selectPriceInfoByAreaId(cri, areaId));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(areaId));

        model.addAttribute("pageMaker", pageMaker);
    return "admin/infoList";
    }

    @PostMapping(value = "/priceInfo/register")
    public String createPriceInfo(PriceInfo info){
      appService.createPriceInfo(info);
/*TODO SMS*/
        /*log.info("create price Info success" + info.toString());
        return  "redirect:" + "/sendSMS";
        */
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
    /* 난가 정보 관리 끝*/

    /*  난가 예보 관리 */
    @GetMapping(value = "/priceCast/list")
    public String listPriceCast(@ModelAttribute("cri") Criteria cri, Model model) {
        model.addAttribute("priceCastList", appService.selectPriceCastList(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        return "admin/castList";
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
    /* 난가 예보 관리 끝*/

    /*  게시판 정보 관리 */
    @GetMapping(value = "/board/list")
    public String listBoard(@ModelAttribute("cri") Criteria cri, Integer boardId, String serarchText, Model model) {
        model.addAttribute("boardList", appService.selectBoardMessageList(boardId, 1, serarchText, cri));
        model.addAttribute("areaList", appService.selectBoardList());

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectBoardMessageListCount(boardId, 1, serarchText));

        model.addAttribute("pageMaker", pageMaker);
        return "admin/boardList";
    }

    @GetMapping(value = "/board/register")
    public String registerBoard(Model model){
        model.addAttribute("board", new BoardMessage());
        model.addAttribute("areaList", appService.selectBoardList());
        model.addAttribute("register",true);
        return  "admin/board";
    }

    @PostMapping(value = "/board/register")
    public String createBoard(BoardMessage board){
        appService.createBoardMessage(board);
        return  "redirect:/admin/board/list";
    }

    @GetMapping(value = "/board/read")
    public String readBoard(long id, Model model) {
        model.addAttribute("board", appService.selectBoardMessage(id));
        return "admin/board";
    }

    @GetMapping(value = "/board/mod")
    public String modBoard(long id, Model model) {
        model.addAttribute("board", appService.selectBoardMessage(id));
        model.addAttribute("areaList", appService.selectBoardList());
        model.addAttribute("modify",true);
        return "admin/board";
    }

    @PostMapping(value = "/board/mod")
    public String modBoard(BoardMessage board){
        appService.updateBoardMessage(board);
        return  "redirect:/admin/board/list";
    }

    @GetMapping(value = "/board/remove")
    public String removeBoard(long boardId){
        appService.deleteBoardMessage(boardId);
        return  "redirect:/admin/board/list";
    }
    /* 게시판 관리 끝*/

    /*  문의 관리 */
    @GetMapping(value = "/contact/list")
    public String listContact(@ModelAttribute("cri") Criteria cri, Integer status, Model model) {
        model.addAttribute("boardList", appService.selectContactUsList(null, status, cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountContactUs(null, status));

        model.addAttribute("pageMaker", pageMaker);
        return "admin/contactList";
    }

    @GetMapping(value = "/contact/read")
    public String readContact(long id, Model model) {
        model.addAttribute("contact", appService.selectContactUs(id));
        return "admin/contact";
    }

    @PostMapping(value = "/contact/answer")
    public String answerContact(ContactUs contact){
        appService.updateContactUs(contact);
        return  "redirect:/admin/contact/list";
    }
    /* 문의 관리 끝*/

    /*  상품 관리 */
    @GetMapping(value = "/product/list")
    public String listProduct(@ModelAttribute("cri") Criteria cri, Model model) {
        //model.addAttribute("priceCastList", appService.selectPriceCast(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        //pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        return "admin/productList";
    }

    @GetMapping(value = "/product/register")
    public String registerProduct(){
        return  "admin/product";
    }

    @PostMapping(value = "/product/register")
    public String createProduct(Product goods){
        //appService.createBoardMessage(goods);
        return  "redirect:/admin/product/list";
    }

    @GetMapping(value = "/product/read")
    public String readProduct(long id, Model model) {
        //model.addAttribute("board", appService.selectBoardMessage(id));
        return "admin/product";
    }

    @GetMapping(value = "/product/mod")
    public String modProduct(long id, Model model) {
        //model.addAttribute("board", appService.selectBoardMessage(id));
        return "admin/product";
    }

    @PostMapping(value = "/product/mod")
    public String modProduct(Product goods){
        //appService.updateBoardMessage(board);
        return  "redirect:/admin/product/list";
    }

    @GetMapping(value = "/product/remove")
    public String removeProduct(long productId){
        //appService.deletePriceCast(castId);
        return  "redirect:/admin/product/list";
    }
    /* 상품 관리 끝*/

    /*  입금 관리 */
    @GetMapping(value = "/deposit/list")
    public String listDeposit(@ModelAttribute("cri") Criteria cri,
                              String userId, String userName, Integer status, String reqName, String memo, Date startDate, Date endDate
                              ,Model model) {
        model.addAttribute("infoList", userService.selectAllUserRoleReqList(cri, userId, userName, status, reqName, memo, startDate, endDate));

        List<UserRoleReq> reqList = new ArrayList();
        reqList.add(new UserRoleReq());
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(userService.selectCountAllUserRoleReqList(userId, userName, status, reqName, memo, startDate, endDate));

        model.addAttribute("pageMaker", pageMaker);
        return "admin/depositList";
    }

    @GetMapping(value = "/deposit/register")
    public String registerDeposit(){
        return  "admin/deposit";
    }

    @PostMapping(value = "/deposit/register")
    public String createDeposit(UserRoleReq req){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        req.setExpireDate( new Timestamp(cal.getTime().getTime()));
        req.setUpdateDate( new Timestamp(new Date().getTime()));
        userService.createUserRoleReq(req);
        return  "redirect:/admin/deposit/list";
    }

    @PostMapping(value = "/deposit/mod")
    public String modDeposit(UserRoleReq req){
        req.setStatus(CODE.END.getCode());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        req.setExpireDate( new Timestamp(cal.getTime().getTime()));
        req.setUpdateDate( new Timestamp(new Date().getTime()));
        userService.updateUserRoleReq(req);
        return  "redirect:/admin/deposit/list";
    }

    @GetMapping(value = "/deposit/remove")
    public String removeDeposit(long id){

        return  "redirect:/admin/deposit/list";
    }
    /* 입금 관리 끝*/

}
