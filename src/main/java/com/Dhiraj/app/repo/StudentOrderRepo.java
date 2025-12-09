package com.Dhiraj.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Dhiraj.app.dto.StudentOrder;

public interface StudentOrderRepo extends JpaRepository<StudentOrder, Integer>{
    public StudentOrder findByRazorpayOrderId(String orderId);

}
