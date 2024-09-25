package com.digidinos.shopping.serviceWithRepo;

import com.digidinos.shopping.entity.Product;
import com.digidinos.shopping.form.ProductForm;
import com.digidinos.shopping.model.ProductInfo;
import com.digidinos.shopping.pagination.PaginationResult;
import com.digidinos.shopping.repository.ProductRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public PaginationResult<ProductInfo> listProducts(int page, int maxResult, int maxNavigationPage, String likeName) {
        Pageable pageable = PageRequest.of(page - 1, maxResult, Sort.by("createDate").descending());

        // Tìm kiếm sản phẩm nếu có từ khóa và phân trang
        Page<Product> productPage;
        if (likeName != null && !likeName.isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCase(likeName, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        // Chuyển đổi từ Page<Product> sang PaginationResult<ProductInfo>
        return new PaginationResult<>(productPage.map(product -> new ProductInfo(
            product.getCode(), product.getName(), product.getPrice(), product.getRepo()
        )), page, maxNavigationPage);
    }

    public ProductInfo findProductInfo(String code) {
        Product product = productRepository.findByCode(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice(), product.getRepo());
    }

    public void deleteProductByCode(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    
    public Product findProduct(String code) {
        return productRepository.findByCode(code);
    }
    
    public void save(ProductForm productForm) {
        Product product = new Product();
        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setRepo(productForm.getRepo());
        product.setCreateDate(new Date());
        
        // Thêm các thuộc tính khác nếu cần

        productRepository.save(product);
    }
    
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName) {
        return listProducts(page, maxResult, maxNavigationPage, likeName);
    }
}
