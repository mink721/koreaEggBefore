package cc.koreaEgg.controller;

import cc.koreaEgg.entity.CartData;
import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
//@SessionAttributes({"cart", "name", "age"})
@SessionAttributes("cart")
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * Method to Add Products to the Shopping Cart First Check if the Product is
	 * available in the Wishlist, if available, remove the product from Wishlist
	 * 
	 * @author Sai Upadhyayula
	 * 

	 * @return Product Page View
	 */
	@RequestMapping(value = "/cart/add", method = RequestMethod.GET)
	public String addProducts(Model model,
							  @RequestParam(value = "id") Long productId,
							  @RequestParam(defaultValue = "1") Integer qty,
							  HttpSession session, @AuthenticationPrincipal User user) {
		// When a customer adds a product to the cart, we have to check
		// if he is a registered or an anonymous customer.

		CartData cartData = (CartData)session.getAttribute("cart");
		if (cartData == null) {
			// Creates a new cart
			cartData = new CartData();
		}

		cartService.addProduct(user, cartData, productId, qty);
		model.addAttribute("cart", cartData );

		return "redirect:/cart";

	}

	/**
	 * Method to View the Items of Shopping Cart Retrieves the items to display
	 * in the shopping cart page
	 * 
	 * @author SaiUpadhyayula

	 * @return Shopping Cart View
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String viewCart(Model model, HttpSession session) {

		CartData cartData = (CartData)session.getAttribute("cart");
		if (cartData == null) {
			return "/order/empty-cart";
		}

		return "/order/cart";
	}

	/**
	 * Method to Update the shopping cart page
	 * 
	 * @author Sai Upadhyayula
	 * 

	 * @return Shopping Cart View
	 */

	/*@RequestMapping(value = "/cart/mod", method = RequestMethod.POST)
	public String updateCart(Model model, Long id, Integer qty, HttpSession session) {
		CartData cartData = (CartData)session.getAttribute("cart");
		cartService.updateProduct(cartData, id, qty);
		return "redirect:/cart";
	}*/

	/**
	 * Method to Remove the Products from shopping cart
	 * 
	 * @author Sai Upadhyayula
	 * 

	 * @return Shopping Cart View
	 */

	@RequestMapping(value = "/cart/remove", method = RequestMethod.GET)
	public String removeProduct(
			@RequestParam(value = "id") Long productId, Model model,
			HttpSession session, @AuthenticationPrincipal User user) {

		CartData cartData = (CartData)session.getAttribute("cart");
		if (cartData != null) {
			cartService.removeProduct(user, cartData, productId);
		}

		return "redirect:/cart";
	}

	@ResponseBody
	@DeleteMapping(value = "/cart/remove")
	public String ajaxRemoveProduct(
			@RequestParam(value = "id") Long id, Model model,
			HttpSession session, @AuthenticationPrincipal User user) {

		try {
			CartData cartData = (CartData)session.getAttribute("cart");
			if (cartData != null) {
				cartService.removeProduct(user, cartData, id);
			}
			return "sucess";
		}catch (Exception e){
			log.error(e.getCause().toString());
			return "fail";
		}
	}

}