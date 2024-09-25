package com.digidinos.shopping.repository;

import com.digidinos.shopping.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Tìm sản phẩm theo code
    Product findByCode(String code);

 // Tìm sản phẩm có tên chứa chuỗi likeName (không phân biệt hoa thường) và hỗ trợ phân trang
    Page<Product> findByNameContainingIgnoreCase(String likeName, Pageable pageable);
}

