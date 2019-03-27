package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.ProductService;
import cc.koreaEgg.service.UserService;
import cc.koreaEgg.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
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
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminHome() {
    return "admin/home";
    }


    /*  유저 관리  */
    @GetMapping(value = "/user/list")
    public String listUser(@ModelAttribute("cri") Criteria cri, @ModelAttribute("userId") String userId,
                           @ModelAttribute("userName") String userName, @ModelAttribute("phone") String phone,
                           @ModelAttribute("shopName") String shopName, @ModelAttribute("address") String address,
                           @ModelAttribute("role") String role,
                              Model model) {
        //유효기간 지난 롤들 변경
        userService.refreshRole();

        List<String> search = Arrays.asList("userId", "userName", "phone", "shopName", "address", "role");

        List<User> userList = userService.selectAllUser(cri, userId, userName, phone, shopName, address, role);
        model.addAttribute("userList", userList);

         PageMaker pageMaker = new PageMaker();
         pageMaker.setCri(cri);
         pageMaker.setTotalCount(userService.selectCountAllUser(userId, userName, phone, shopName, address, role));

         model.addAttribute("pageMaker", pageMaker);
         model.addAttribute("search", search);

    return "admin/userList";
    }

    @GetMapping(value = "/user/read")
    public String readUser(long id, Model model) {
    model.addAttribute("user", userService.selectUserById(id));
    return "admin/user";
    }

    @GetMapping(value = "/user/mod")
    public String modUser(long id, Model model) {
        model.addAttribute("user", userService.selectUserById(id));
        model.addAttribute("modify",true);
        return "admin/user";
    }

    @PostMapping(value = "/user/mod")
    public String modUser(@Valid @ModelAttribute("user")User user, BindingResult result, Model model) {

        if( LogUtils.bindingResult(result) ){
            model.addAttribute("modify",true);
            return "admin/user";
        }

        userService.updateUser(user);
        return "redirect:/admin/user/list";
    }

    @GetMapping(value = "/user/remove")
    public String removeUser(long id, Model model) {
        userService.deleteUser(id);
        return  "redirect:/admin/user/list";
    }
    /*  유저 관리 끝  */


    /*  난가 정보 관리 */
    @GetMapping(value = "/priceInfo/list")
    public String listPriceInfo(@ModelAttribute("cri") Criteria cri, Integer areaId ,Model model) {

        List<String> search = Arrays.asList("areaId");

        model.addAttribute("areaList", appService.selectAreaList());
        model.addAttribute("priceInfoList", appService.selectPriceInfoByAreaId(cri, areaId));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceInfoByAreaId(areaId));

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("search", search);
        model.addAttribute("areaId", areaId);

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

        List<String> search = Arrays.asList();

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("search", search);
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
    public String listBoard(@ModelAttribute("cri") Criteria cri, Integer boardId, String searchText, Model model) {

        model.addAttribute("boardList", appService.selectBoardMessageList(boardId, 1, searchText, cri));
        model.addAttribute("areaList", appService.selectBoardList());

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(appService.selectBoardMessageListCount(boardId, 1, searchText));

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("boardId", "searchText");
        model.addAttribute("search", search);
        model.addAttribute("boardId", boardId);

        return "admin/boardList";
    }

    @GetMapping(value = "/board/read")
    public String readBoard(long id, Model model) {
        model.addAttribute("board", appService.selectBoardMessage(id));
        return "admin/board";
    }

    @GetMapping(value = "/board/register")
    public String registerBoard(Model model){

        if( !model.containsAttribute("board")){
            model.addAttribute("board", new BoardMessage());
        }

        model.addAttribute("selectList", appService.selectBoardList());
        model.addAttribute("register",true);
        return  "admin/board";
    }

    @PostMapping(value = "/board/register")
    public String createBoard(@Valid @ModelAttribute("board")BoardMessage board, BindingResult result, Model model){
        if( LogUtils.bindingResult(result) ){
            return registerBoard(model);
        }
        appService.createBoardMessage(board);
        return  "redirect:/admin/board/list";
    }



    @GetMapping(value = "/board/mod")
    public String modBoard(long id, Model model) {
        model.addAttribute("board", appService.selectBoardMessage(id));
        model.addAttribute("selectList", appService.selectBoardList());
        model.addAttribute("modify",true);
        return "admin/board";
    }

    @PostMapping(value = "/board/mod")
    public String modBoard(@Valid @ModelAttribute("board")BoardMessage board, BindingResult result, Model model){

        if( LogUtils.bindingResult(result) ){
            return registerBoard(model);
        }

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

        List<String> search = Arrays.asList("status");
        model.addAttribute("search", search);
        model.addAttribute("status", status);

        return "admin/contactList";
    }

    @GetMapping(value = "/contact/read")
    public String readContact(long id, Model model) {
        model.addAttribute("contact", appService.selectContactUs(id));
        return "admin/contact";
    }

    @GetMapping(value = "/contact/mod")
    public String modContact(long id, Model model) {
        model.addAttribute("contact", appService.selectContactUs(id));
        model.addAttribute("mod", true);
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
    public String listProduct(@ModelAttribute("cri") Criteria cri,  Integer size, Model model) {
        model.addAttribute("productList", productService.selectProductList(cri, null, null, size));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        //pageMaker.setTotalCount(appService.selectCountPriceCast());

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("size");
        model.addAttribute("search", search);
        model.addAttribute("size", size);

        return "admin/productList";
    }

    @GetMapping(value = "/product/register")
    public String registerProduct(Model model){
        model.addAttribute("register",true);

        if( !model.containsAttribute("product")){
            model.addAttribute("product", new Product());
        }

        return  "admin/product";
    }

    @PostMapping(value = "/product/register")
    public String createProduct(@Valid @ModelAttribute("product")Product product,
                                BindingResult result, Model model){
        if( LogUtils.bindingResult(result) ){
            return registerBoard(model);
        }
        productService.createProduct(product);
        return  "redirect:/admin/product/list";
    }

    @GetMapping(value = "/product/read")
    public String readProduct(long id, Model model) {
        model.addAttribute("product", productService.selectProduct(id, null));
        return "admin/product";
    }

    @GetMapping(value = "/product/mod")
    public String modProduct(long id, Model model) {
        model.addAttribute("product", appService.selectBoardMessage(id));
        return "admin/product";
    }

    @PostMapping(value = "/product/mod")
    public String modProduct(@Valid @ModelAttribute("product")Product product,
                             BindingResult result, Model model){

        if( LogUtils.bindingResult(result) ){
            return registerProduct(model);
        }

        productService.updateProduct(product);
        return  "redirect:/admin/product/list";
    }

    @PostMapping(value = "/product/modPrice")
    public String modProduct(@ModelAttribute("product")Price price,
                             Model model){

        productService.updatePrice(price);
        return  "redirect:/admin/product/list";
    }


    @GetMapping(value = "/product/remove")
    public String removeProduct(long id){

        productService.deleteProduct(id);
        return  "redirect:/admin/product/list";
    }
    /* 상품 관리 끝*/

    /*  입금 관리 */
    @GetMapping(value = "/deposit/list")
    public String listDeposit(@ModelAttribute("cri") Criteria cri,
                              String userId, String userName,
                              Integer status, String reqName,
                              String memo
                              ,Model model) {

        Timestamp startDate = null;
        Timestamp endDate = null;
        model.addAttribute("infoList", userService.selectAllUserRoleReqList(cri, userId, userName, status, reqName, memo, startDate, endDate));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(userService.selectCountAllUserRoleReqList(userId, userName, status, reqName, memo, startDate, endDate));

        model.addAttribute("pageMaker", pageMaker);

        List<String> search = Arrays.asList("userId", "userName", "status", "reqName", "memo", "startDate", "endDate");
        model.addAttribute("search", search);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("status", status);
        model.addAttribute("reqName", reqName);
        model.addAttribute("memo", memo);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "admin/depositList";
    }

    @PostMapping(value = "/deposit/register")
    public String createDeposit(@Valid @ModelAttribute("req")UserRoleReq req,
                                BindingResult result, Model model){

        if( LogUtils.bindingResult(result) ){
            throw new ValidationException();
        }
        req.setExpireDate( getAfterYear() );
        req.setUpdateDate( new Timestamp(new Date().getTime()));
        userService.createUserRoleReq(req);
        return  "redirect:/admin/deposit/list";
    }

    private Timestamp getAfterYear(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Timestamp tm = new Timestamp(cal.getTime().getTime());
        return tm;
    }

    @PostMapping(value = "/deposit/mod")
    public String modDeposit(@Valid @ModelAttribute("req")UserRoleReq req,
                             BindingResult result, Model model){

        if( LogUtils.bindingResult(result) ){
            return "admin/depositList";
        }

        req.setStatus(CODE.END.getCode());
        req.setExpireDate( getAfterYear() );
        req.setUpdateDate( new Timestamp(new Date().getTime()));
        userService.updateUserRoleReq(req);
        return  "redirect:/admin/deposit/list";
    }

    @GetMapping(value = "/deposit/remove")
    public String removeDeposit(long id){

        /*TODO delete*/
        return  "redirect:/admin/deposit/list";
    }
    /* 입금 관리 끝*/

}
