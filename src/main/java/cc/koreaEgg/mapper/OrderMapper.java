package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
	void createOrder(Order order);
	void createOrderItem(Order order);

	@MapKey("productId")
	Map<Long, OrderItem> selectCart(User user);

	void createCart(long userId, Long productId, Integer qty);
	void updateCart(long userId, Long productId, Integer qty);

	List<Product> readAllOrderItems(Order order);

	List<Order> readAllOrdersForCustomer(User customer);

	Order readOrderById(Long orderId);

    void deleteCart(@Param("id")long id, @Param("productId") long productId);

    List<Order> selectOrderList(Criteria cri, Long id);
    List<Order> selectOrderListByPartner(Criteria cri, Long id);

}
