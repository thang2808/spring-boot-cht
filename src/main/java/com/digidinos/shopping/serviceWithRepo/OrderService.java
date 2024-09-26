package com.digidinos.shopping.serviceWithRepo;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digidinos.shopping.entity.Order;
import com.digidinos.shopping.entity.OrderDetail;
import com.digidinos.shopping.model.CartInfo;
import com.digidinos.shopping.model.CartLineInfo;
import com.digidinos.shopping.model.OrderDetailInfo;
import com.digidinos.shopping.model.OrderInfo;
import com.digidinos.shopping.pagination.PaginationResult;
import com.digidinos.shopping.repository.OrderDetailRepository;
import com.digidinos.shopping.repository.OrderRepository;
import com.digidinos.shopping.repository.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	
	@Transactional(rollbackFor = Exception.class)
    public void saveOrder(CartInfo cartInfo) {
        int orderNum = getMaxOrderNum() + 1;

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());
        // Set thông tin khách hàng từ CartInfo
        order.setCustomerName(cartInfo.getCustomerInfo().getName());
        order.setCustomerEmail(cartInfo.getCustomerInfo().getEmail());
        order.setCustomerPhone(cartInfo.getCustomerInfo().getPhone());
        order.setCustomerAddress(cartInfo.getCustomerInfo().getAddress());

        orderRepository.save(order);

        for (CartLineInfo line : cartInfo.getCartLines()) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuanity(line.getQuantity());
            detail.setProduct(productRepository.findByCode(line.getProductInfo().getCode()));

            orderDetailRepository.save(detail);
        }

        cartInfo.setOrderNum(orderNum);
    }
	
	private int getMaxOrderNum() {
	    return orderRepository.findMaxOrderNum();
	}

	public List<OrderDetailInfo> listOrderDetailInfo(String orderId) {
	    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
	    return orderDetails.stream()
	            .map(detail -> new OrderDetailInfo(
	                detail.getId(),
	                detail.getProduct().getCode(),  // productCode
	                detail.getProduct().getName(),   // productName
	                detail.getQuanity(),             // quanity
	                detail.getPrice(),                // price
	                detail.getAmount()                // amount
	            ))
	            .collect(Collectors.toList());
	}
    
    public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
        Pageable pageable = PageRequest.of(page - 1, maxResult, Sort.by("orderNum").descending());
        Page<Order> orderPage = orderRepository.findAll(pageable);

        // Chuyển dữ liệu từ Page sang PaginationResult
        return new PaginationResult<>(orderPage.map(order -> new OrderInfo(
            order.getId(), order.getOrderDate(), order.getOrderNum(),
            order.getAmount(), order.getCustomerName(), order.getCustomerAddress(),
            order.getCustomerEmail(), order.getCustomerPhone(), order.getOrderStatus()
        )), page, maxNavigationPage);
    }
    
    public Order findOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    public OrderInfo getOrderInfo(String orderId) {
        Order order = this.findOrder(orderId);
        if (order == null) {
            return null;
        }
        return new OrderInfo(
            order.getId(),
            order.getOrderDate(),
            order.getOrderNum(),
            order.getAmount(),
            order.getCustomerName(),
            order.getCustomerAddress(),
            order.getCustomerEmail(),
            order.getCustomerPhone(),
            order.getOrderStatus()
        );
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, String status) {
        // Tìm kiếm đơn hàng theo ID
        Order order = this.findOrder(orderId);
        
        if (order != null) {
            // Cập nhật trạng thái đơn hàng
            order.setOrderStatus(status);
            // Lưu đơn hàng sau khi cập nhật trạng thái
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found!");
        }
    }
    
    //tim don hang theo gmail 
    public PaginationResult<OrderInfo> listOrderInfoByEmail(String email, int page, int maxResult, int maxNavigationPage) {
        Pageable pageable = PageRequest.of(page - 1, maxResult, Sort.by("orderNum").descending());
        Page<Order> orderPage = orderRepository.findByCustomerEmail(email, pageable);

        // Chuyển dữ liệu từ Page sang PaginationResult
        return new PaginationResult<>(orderPage.map(order -> new OrderInfo(
            order.getId(), order.getOrderDate(), order.getOrderNum(),
            order.getAmount(), order.getCustomerName(), order.getCustomerAddress(),
            order.getCustomerEmail(), order.getCustomerPhone(), order.getOrderStatus()
        )), page, maxNavigationPage);
    }

}
