package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {

	private Long id;
	private Timestamp regDate;
	private Timestamp updatedDate;
	private String status; //CODE.ORDER
	private int total;
	private Long userId;


	private String orderName;
	private String tel;
	private String mobile;
	private String addr;
	private String memo;
	private String depositor;

	private List<OrderItem> itemList;

}
