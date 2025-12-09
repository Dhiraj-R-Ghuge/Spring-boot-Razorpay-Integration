package com.Dhiraj.app.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Dhiraj.app.dto.StudentOrder;
import com.Dhiraj.app.repo.StudentOrderRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import java.util.Map;

// for razorpay transaction order details we can create this logic means order email ,order id ,who can order it , how many time it done  etc
// that's why the logic is created
@Service
public class StudentService {
	@Autowired
	private StudentOrderRepo studentrepo;
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	@Value("${razorpay.secret.key}")
	private String razorPaySecret;
	
	private RazorpayClient client; //It is used to interact with Razorpay APIs for payment processing, orders, refunds, etc.
	
	public StudentOrder createOrder(StudentOrder stuorder) throws Exception {
		
		
		JSONObject orderReq=new JSONObject();
		orderReq.put("amount", stuorder.getAmount()*100); // amount in paisa
		orderReq.put("currency", "INR" );
		orderReq.put("receipt", stuorder.getEmail());
		
		this.client=new RazorpayClient(razorPayKey,razorPaySecret);
		
		//create order in razorpay
		Order razorPayOrder=client.orders.create(orderReq);
		
		System.out.println(razorPayOrder);
		
		stuorder.setRazorpayOrderId(razorPayOrder.get("id"));
		stuorder.setOrderStatus(razorPayOrder.get("status"));
		
		studentrepo.save(stuorder);
		
		return stuorder;
		
		
	}
	public StudentOrder updateOrder(Map<String, String>responsePayLoad){
		String razorPayOrderId=responsePayLoad.get("razorpay_order_id");

		StudentOrder order=studentrepo.findByRazorpayOrderId(razorPayOrderId);
		order.setOrderStatus("Payment Completed");

		StudentOrder updatedOrder =studentrepo.save(order);
		return  updatedOrder;


	}

}
