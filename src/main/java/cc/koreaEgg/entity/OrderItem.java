package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
public class OrderItem{

	private Long id;
	private Long orderId;
	private Long productId;

	private int price;
	private int qty;

	private String productName;
	private String imgPath;

	public void changeQty(Integer qty) {
		if(qty == null){
			this.qty = 1;
		}else {
			this.qty = qty;
		}
	}

	public void addQty(Integer qty) {
		if(qty == null){
			qty = 1;
		}
			this.qty += qty;
	}

	public int getTotal() {
		int amount = 0;
			amount += price * qty;
		return amount;
	}

}