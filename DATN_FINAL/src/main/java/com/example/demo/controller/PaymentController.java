package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.config.PaypalPaymentIntent;
import com.example.demo.config.PaypalPaymentMethod;
import com.example.demo.entities.Bill;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.BillServiceImp;
import com.example.demo.service.impl.PaypalService;
import com.example.demo.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {

	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaypalService paypalService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BillServiceImp billService;

	@RequestMapping(value = "/payMoney", method = RequestMethod.POST)
	public String pay(HttpServletRequest request, @RequestParam("price") double price,
			@RequestParam("paymentType") String type,  Principal principal) {
		if (type.equals("cash")) {
			User user = userRepository.findByUsernameEquals(principal.getName());
			
			Bill bill = billService.saveBill(user, "Thanh toán khi nhận hàng");
			
			return "redirect:/bill/history";
		}
		
		String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
		String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
		try {
			Payment payment = paypalService.createPayment(price, "USD", PaypalPaymentMethod.paypal,
					PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
			for (Links links : payment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/checkouts";
	}

	@RequestMapping(URL_PAYPAL_CANCEL)
	public String cancelPay() {
		return "cancel";
	}

	@RequestMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			Principal principal) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				User user = userRepository.findByUsernameEquals(principal.getName());
				Bill bill = billService.saveBill(user, "Thanh toán onl");
				return "redirect:/bill/history";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/bill/history";
	}

}
