package com.digidinos.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digidinos.shopping.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
	Order findByOrderNum(int orderNum);
	
	@Query("SELECT COALESCE(MAX(o.orderNum), 0) FROM Order o")
    Integer findMaxOrderNum();
	
	// Tìm kiếm đơn hàng theo email khách hàng và có phân trang
    Page<Order> findByCustomerEmail(String email, Pageable pageable);
}
