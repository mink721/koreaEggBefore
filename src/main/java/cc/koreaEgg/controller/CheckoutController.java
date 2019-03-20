package cc.koreaEgg.controller;

import cc.koreaEgg.entity.CartData;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.CartService;
import cc.koreaEgg.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CheckoutController {

	@Autowired
	private CartService cartService;
//	@Autowired
//	private ProductConfigService productService;
	@Autowired
	//private AddressService addressService;
//	@Autowired
//	private PaymentService paymentService;

	private HttpSession session;

	@RequestMapping(value = "/order/checkout")
	public String checkOutCart(Model model, @AuthenticationPrincipal User customer, HttpServletRequest request) {

		CartData cartData = (CartData)session.getAttribute("cart");
		if(cartData.getTotal() == 0){
			return "/order/cart";
		}
			return "/order/checkout";
	}

	/*@RequestMapping(value = "/address", method = RequestMethod.POST)
	public String validateAddressInformation(
			@ModelAttribute("addressForm") AddressForm address, Model model,
			HttpServletRequest request) {
		String fullName = request.getParameter("fullName");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String zipCode = request.getParameter("zipCode");
		String state = request.getParameter("state");

		address.setFullName(fullName);
		address.setAddress1(address1);
		address.setAddress2(address2);
		address.setCity(city);
		address.setZipCode(zipCode);
		address.setState(state);
		session = SessionUtils.createSession(request);
		Customer customer = SessionUtils.getSessionVariables(request, "customer");
		addressService.saveAddress(address, customer);
		SessionUtils.setSessionVariables(address, request, "address");
//		model.addAttribute("prodList", cartService.getProductsList());
		return "redirect:checkout";
	}*/

	/*@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String getPaymentForm(Model model, HttpServletRequest request) {
		return "payment";
	}*/

}
