package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.CartService;
import cc.koreaEgg.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;

	private static String orderHistoryPage = "template/ordersList";
	private static String orderDetailsPage = "template/orderDetails";

	@RequestMapping(value = "/order/checkout")
	public String checkOutCart(Model model, @AuthenticationPrincipal User customer, HttpSession session) {

		CartData cartData = (CartData)session.getAttribute("cart");
		if(cartData.getTotal() == 0){
			return "/order/cart";
		}
		return "/order/checkout";
	}

	@RequestMapping(value = "/order/register", method = RequestMethod.POST)
	public String registerOreder(Model model, HttpSession session, Order order) {

		CartData cartData = (CartData)session.getAttribute("cart");
		order.setItemList(new ArrayList<OrderItem>(cartData.getProductsMap().values()));
		order.setTotal(cartData.getTotal());
		orderService.createOrder(order);
		cartData.clearCart();

		return "/order/order-completed";
	}


	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public String getOrderConfirmPage(@ModelAttribute("cri") Criteria cri, @AuthenticationPrincipal User user
									  ,Model model, HttpServletRequest request) {

		List<Order> orderList = orderService.selectOrderList(cri, user.getId());
		model.addAttribute("orderList", orderList);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(10);

		model.addAttribute("pageMaker", pageMaker);

		List<String> search = Arrays.asList();
		model.addAttribute("search", search);

		return "/user/orderList";
	}

	@RequestMapping(value = "/order/read", method = RequestMethod.GET)
	public String getOrderDetails(HttpServletRequest request, Model model,
								  @AuthenticationPrincipal User user,
								  @RequestParam(value = "id") Long id) {

		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/user/order";
	}
}
