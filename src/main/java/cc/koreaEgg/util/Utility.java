package cc.koreaEgg.util;

import cc.koreaEgg.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static Long generateOrderNumber(Date date, User customer) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String formattedDate = dateFormat.format(date);
		Long orderId = Long.parseLong(formattedDate)
				* (customer.getId());
		return orderId;
	}

	public static Long generateAddressNumber(User customer) {
		return (long) (Long.parseLong(customer.getPostNum())
				* customer.getId() * Math.random());
	}
}