package com.digidinos.shopping.form;

import org.springframework.web.multipart.MultipartFile;
import com.digidinos.shopping.entity.Product;

public class ProductForm {
    private String code;
    private String name;
    private double price;
    private int repo;


    private boolean newProduct = false;


    // Upload file.
    private MultipartFile fileData;


    public ProductForm() {
        this.newProduct= true;
    }


    public ProductForm(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.repo = product.getRepo();
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


    public MultipartFile getFileData() {
        return fileData;
    }


    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }


    public boolean isNewProduct() {
        return newProduct;
    }


    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }


	public int getRepo() {
		return repo;
	}


	public void setRepo(int repo) {
		this.repo = repo;
	}
    
    

}
