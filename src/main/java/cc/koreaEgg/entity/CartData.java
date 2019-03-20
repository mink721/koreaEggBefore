package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
public class CartData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Long, OrderItem> productsMap;
	private int total;

	public CartData() {
		productsMap = new ConcurrentHashMap<Long, OrderItem>();
	}

	public void add(Long productId, OrderItem orderItem) {
		productsMap.put(productId, orderItem);
	}

	public boolean contains(Long productId) {
		return productsMap.containsKey(productId);
	}

	public void changeProductQuantity(Long productId, Integer qty) {
		OrderItem orderItem = productsMap.get(productId);
		orderItem.changeQty(qty);
	}

	public void clearCart() {
		productsMap.clear();
	}

	public void removeProduct(Long productId) {
		productsMap.remove(productId);
	}

	/*public List<OrderItem> getOrderItemsList() {
		// HashMap.values() returns a Collection which cannot be cast into List
		// Thus create an ArrayList and set it as a constructor.
		orderItemsList = new ArrayList<OrderItem>(productsMap.values());
		return orderItemsList;
	}*/

	public int getTotal() {

		int amount = 0;
		for (OrderItem item : productsMap.values()) {
			amount = amount + item.getTotal();
		}
		total = amount;
		return total;
	}

	public boolean isCartEmpty() {
		return productsMap.isEmpty();
	}

	public OrderItem getProduct(Long productId) {
		return productsMap.get(productId);
	}

	public int getCartSize() {
		return productsMap.size();
	}

	public boolean containsKey(Long productId) {
		return productsMap.containsKey(productId);
	}
}
