package com.digidinos.shopping.serviceWithRepo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.digidinos.shopping.entity.Comment;
import com.digidinos.shopping.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findCommentsByProductCode(String code) {
        return commentRepository.findByProductCode(code); // phương thức này không có phân trang
    }

    public Page<Comment> findCommentsByProductCode(String code, Pageable pageable) {
        return commentRepository.findByProductCode(code, pageable); // phương thức này có phân trang
    }


    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

}