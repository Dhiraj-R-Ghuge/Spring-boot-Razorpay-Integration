package com.Dhiraj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.Dhiraj.app.dto.StudentOrder;
import com.Dhiraj.app.service.StudentService;

import java.util.Map;

@Controller
public class StudentController {
	@Autowired
	private StudentService service;
	
	@GetMapping("/")
	public String init() {
		return "index";
	}
	@PostMapping(value="/create-order", produces="application/json")
	@ResponseBody
	public ResponseEntity<StudentOrder> createOrder(@RequestBody StudentOrder studentOrder) throws Exception {
	    StudentOrder createdOrder = service.createOrder(studentOrder);
	    return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	}
	@PostMapping("/handle-payment-callback")
	public String handlePaymentcallback(@RequestParam Map<String,String>respPayload){
		System.out.println(respPayload);
		service.updateOrder(respPayload);

		return "success";

	}



}
