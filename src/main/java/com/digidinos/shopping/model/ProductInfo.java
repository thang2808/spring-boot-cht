package com.digidinos.shopping.model;

import com.digidinos.shopping.entity.Product;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private int repo;


    public ProductInfo() {
    }


    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.repo = product.getRepo();
    }


    // Sử dụng trong JPA/Hibernate query
    public ProductInfo(String code, String name, double price, int repo) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.repo = repo;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


	public int getRepo() {
		return repo;
	}


	public void setRepo(int repo) {
		this.repo = repo;
	}
    
}
