package com.digidinos.shopping.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import com.digidinos.shopping.entity.Product;
import com.digidinos.shopping.form.ProductForm;
import com.digidinos.shopping.serviceWithRepo.ProductService;

@Component
public class ProductFormValidator implements Validator {


	@Autowired
	private ProductService productService;


	// Validator này chỉ dùng để kiểm tra class ProductForm.
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == ProductForm.class;
	}


	@Override
	public void validate(Object target, Errors errors) {
		ProductForm productForm = (ProductForm) target;


		// Kiểm tra các trường (field) của ProductForm.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repo", "NotEmpty.productForm.repo");


		String code = productForm.getCode();
		if (code != null && code.length() > 0) {
			if (code.matches("\\s+")) {
				errors.rejectValue("code", "Pattern.productForm.code");
			} else if (productForm.isNewProduct()) {
				Product product = productService.findProduct(code);
				if (product != null) {
					errors.rejectValue("code", "Duplicate.productForm.code");
				}
			}
		}
	}


}
