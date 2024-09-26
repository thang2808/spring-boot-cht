package com.digidinos.shopping.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;


    @Id
    @Column(name = "CODE", length = 20, nullable = false)
    private String code;


    @Column(name = "NAME", length = 255, nullable = false)
    private String name;


    @Column(name = "PRICE", nullable = false)
    private double price;


    @Lob
    @Column(name = "IMAGE", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;
    
    @Column(name = "REPO")
    private int repo;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Product() {
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


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }


	public int getRepo() {
		return repo;
	}


	public void setRepo(int repo) {
		this.repo = repo;
	}
    
    

}
