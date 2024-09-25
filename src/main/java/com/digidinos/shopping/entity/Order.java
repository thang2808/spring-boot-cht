package com.digidinos.shopping.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ORDERS", uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Order implements Serializable {


    private static final long serialVersionUID = -2576670215015463100L;


    @Id
    @Column(name = "ID", length = 50)
    private String id;
    
    @Column(name = "ORDER_STATUS")
    private String orderStatus;


    @Column(name = "ORDER_DATE", nullable = false)
    private Date orderDate;


    @Column(name = "ORDER_NUM", nullable = false)
    private int orderNum;


    @Column(name = "AMOUNT", nullable = false)
    private double amount;


    @Column(name = "CUSTOMER_NAME", length = 255, nullable = false)
    private String customerName;


    @Column(name = "CUSTOMER_ADDRESS", length = 255, nullable = false)
    private String customerAddress;


    @Column(name = "CUSTOMER_EMAIL", length = 128, nullable = false)
    private String customerEmail;


    @Column(name = "CUSTOMER_PHONE", length = 128, nullable = false)
    private String customerPhone;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Date getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public int getOrderNum() {
        return orderNum;
    }


    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }


    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerAddress() {
        return customerAddress;
    }


    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }


    public String getCustomerEmail() {
        return customerEmail;
    }


    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }


    public String getCustomerPhone() {
        return customerPhone;
    }


    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

    
}
