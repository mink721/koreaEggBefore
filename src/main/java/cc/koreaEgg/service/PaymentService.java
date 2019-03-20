package cc.koreaEgg.service;

import cc.koreaEgg.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class PaymentService {
	@Autowired
	private PaymentMapper paymentMapper;
	private HttpSession session;


	public void payByCreditCard( ) {
		paymentMapper.payByCreditCard();
	}


	/*public CreditCardForm gatherCardDetails(CreditCardForm creditCardForm,
			HttpServletRequest request) {
		session = request.getSession();
		Long customerId = ((Customer) session.getAttribute("customer"))
				.getCustomerId();
		String creditCardNumber = request.getParameter("creditCardNumber");
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer year = Integer.parseInt(request.getParameter("year"));
		String name = request.getParameter("name");
		String cvvCode = request.getParameter("cvvCode");
		
		creditCardForm.setCustomerId(customerId);
		creditCardForm.setCreditCardNumber(creditCardNumber);
		creditCardForm.setMonth(month);
		creditCardForm.setYear(year);
		creditCardForm.setCvvCode(cvvCode);
		creditCardForm.setName(name);

		return creditCardForm;

	}*/

}
