package cc.koreaEgg.controller;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.service.CartService;
import cc.koreaEgg.service.OrderService;
import cc.koreaEgg.service.PaymentService;
import cc.koreaEgg.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CartService cartService;
//	@Autowired
//	private ProductConfigService productConfigService;
	@SuppressWarnings("unused")
	private HttpSession session;

	private static String orderHistoryPage = "template/ordersList";
	private static String orderDetailsPage = "template/orderDetails";

	@RequestMapping(value = "/createOrderByCC", method = RequestMethod.POST)
	public String createOrder(Model model, @AuthenticationPrincipal User customer,
			HttpServletRequest request, HttpServletResponse response) throws ParseException,
			IOException {

		session = SessionUtils.createSession(request);
		// Retrieve Details about the Cart,Customer and Address Details
		// used to create detailed Order

		//Order order = orderService.createOrder(cartService, customer, request);
		/*payAmountByCreditCard(creditCardForm, request);

		SessionUtils.setSessionVariables(order, request, "orderDetails");
		List<Product> productsList = orderService.getAllOrderItems(order);
		StringBuffer sb = new StringBuffer();
		sb.append("Hello " + customer.getUserName() + "\n");
		sb.append("Thank you for shopping at eShopper.Happy Shopping!!\n");
		sb.append("OrderId-" + order.getOrderId() + "/n");
		sb.append("Products-/n");
		for (Product p : productsList) {
			sb.append(p.getName() + "  Rs." + p.getPrice() + "\n");
		}
		sb.append("Your Order Status is: " + order.getOrderStatus());
		sb.append("You will get further notification.Once your order is processed");
		mailSenderService.sendEmail(customer.getEmailAddress(),
				customer.getUserName(), sb.toString(),
				"Order Confirmation for " + customer.getUserName());*/
		return "redirect:order";
	}

	/*public void payAmountByCreditCard(CreditCardForm creditCardForm,
			HttpServletRequest request) throws IOException {

		creditCardForm = paymentService.gatherCardDetails(creditCardForm,
				request);
		paymentService.payByCreditCard(creditCardForm);
	}*/

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
