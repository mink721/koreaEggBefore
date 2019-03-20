package cc.koreaEgg.service;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;
	private final String PENDING_ORDER_STATUS = "Pending";

	// private final String COMPLETED_ORDER_STATUS = "Completed";


	public Order createOrder(Order order) {

		order.setStatus(PENDING_ORDER_STATUS);

		orderMapper.createOrder(order);
		orderMapper.createOrderItem(order);
		return order;
	}


	public List<Product> getAllOrderItems(Order order) {

		return orderMapper.readAllOrderItems(order);
	}

	public List<Order> getAllOrdersForCustomer(User customer) {

		return orderMapper.readAllOrdersForCustomer(customer);
	}


	public Order getOrderById(Long orderId) {
		return orderMapper.readOrderById(orderId);
	}

	public List<Order> selectOrderList(Criteria cri, long id){
		return orderMapper.selectOrderList(cri, id);
	}

	public List<Order> selectOrderListByPartner(Criteria cri, long id){
		return orderMapper.selectOrderListByPartner(cri, id);
	}

}