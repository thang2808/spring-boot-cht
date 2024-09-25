package com.digidinos.shopping.model;

public class CartLineInfo {
	 
    private ProductInfo productInfo;
    private int quanity;
 
    public CartLineInfo() {
        this.quanity = 0;
    }
 
    public ProductInfo getProductInfo() {
        return productInfo;
    }
 
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
 
    public int getQuantity() {
        return quanity;
    }
 
    public void setQuantity(int quanity) {
        this.quanity = quanity;
    }
 
    public double getAmount() {
        return this.productInfo.getPrice() * this.quanity;
    }
    
}
