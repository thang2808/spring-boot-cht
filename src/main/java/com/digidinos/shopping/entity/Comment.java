package com.digidinos.shopping.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    private String name;
    
    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE", nullable = false)
    private Product product;  // Liên kết với sản phẩm

    public Comment() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
