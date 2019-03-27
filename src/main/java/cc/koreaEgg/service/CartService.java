package cc.koreaEgg.service;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartService {

	/**
	 * This class is used to handle all the Shopping Cart Operations like
	 * Add,Remove,Update the Items.
	 * 
	 * @author Sai Upadhyayula
	 * 
	 */

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderMapper orderMapper;

	// Add Products to Shopping Cart
	public synchronized void addProduct(User user, CartData cartData, Long productId, Integer qty) {

		//카트에 있으면 수량변경
		if (cartData.contains(productId)) {
			cartData.addProductQuantity(productId, qty);
			if(user != null){
				//DB에도 넣기
				addCartItem(user.getId(), cartData, productId, qty);
			}

		} else {
			//카트에 없으면 유저 롤에 맞게 상품넣기
			Role userRole = Role.USER;
			if(user != null){
				userRole = user.getRole();
				//DB에도 넣기
				addCartItem(user.getId(), cartData, productId, qty);
			}
			Product product = productService.selectProduct(productId, userRole);
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(product.getId());
			orderItem.setPrice(product.getPrice());
			orderItem.setQty(qty);
			orderItem.setProductName(product.getName());
			orderItem.setImgPath(product.getImagePath());
			cartData.add(productId, orderItem);
		}
	}

	public synchronized void removeProduct(User user, CartData cartData, Long productId) {

		cartData.removeProduct(productId);

		if(user != null){
			orderMapper.deleteCart(user.getId(), productId);
		}
	}

	public synchronized void updateProduct(CartData cartData, Long productId,
			int quantity) {
		OrderItem orderItem = cartData.getProduct(productId);
		orderItem.changeQty(quantity);
	}


	public CartData getCartByuserId(User user){
		Map<Long, OrderItem> productsMap = orderMapper.selectCart(user);
		CartData cartData = new CartData();
		if( !productsMap.isEmpty()){
			cartData.setProductsMap(productsMap);
		}
		return cartData;
	}

	public void addCartItem(Long userId, CartData cartData, Long productId, Integer qty) {
		if (cartData.contains(productId)) {
			OrderItem item = cartData.getProductsMap().get(productId);
			orderMapper.updateCart(userId, productId, item.getQty());
		} else {
			orderMapper.createCart(userId, productId, qty);
		}

	}
}
