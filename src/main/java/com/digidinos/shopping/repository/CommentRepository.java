package com.digidinos.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.digidinos.shopping.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Phương thức tìm bình luận theo mã sản phẩm mà không có phân trang
    List<Comment> findByProductCode(String code);

    // Phương thức tìm bình luận theo mã sản phẩm với phân trang
    Page<Comment> findByProductCode(String code, Pageable pageable);
}
