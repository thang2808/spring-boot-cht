//package com.digidinos.shopping.controller;
//
//import java.util.List;
//
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.digidinos.shopping.dao.OrderDAO;
//import com.digidinos.shopping.dao.ProductDAO;
//import com.digidinos.shopping.entity.Product;
//import com.digidinos.shopping.form.ProductForm;
//import com.digidinos.shopping.model.OrderDetailInfo;
//import com.digidinos.shopping.model.OrderInfo;
//import com.digidinos.shopping.pagination.PaginationResult;
//import com.digidinos.shopping.validator.ProductFormValidator;
//
//@Controller
//@Transactional 
//public class AdminController {
//
//	@Autowired
//	private OrderDAO orderDAO;
//
//	@Autowired
//	private ProductDAO productDAO;
//
//	@Autowired
//	private ProductFormValidator productFormValidator;
//
//	@InitBinder
//	public void myInitBinder(WebDataBinder dataBinder) {
//		Object target = dataBinder.getTarget();
//		if (target == null) {
//			return;
//		}
//		System.out.println("Target=" + target);
//
//		if (target.getClass() == ProductForm.class) {
//			dataBinder.setValidator(productFormValidator); 
//		}
//	}
//
//	// GET: Hiển thị trang login
//	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
//	public String login(Model model) {
//		return "login";
//	}
//
//	@RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
//	public String accountInfo(Model model) {
//
//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(userDetails.getPassword());
//		System.out.println(userDetails.getUsername());
//		System.out.println(userDetails.isEnabled());
//
//		model.addAttribute("userDetails", userDetails);
//		return "accountInfo";
//	}
//
//	@RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
//	public String orderList(Model model, //
//			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
//		int page = 1;
//		try {
//			page = Integer.parseInt(pageStr);
//		} catch (Exception e) {
//		}
//		final int MAX_RESULT = 5;
//		final int MAX_NAVIGATION_PAGE = 10;
//
//		PaginationResult<OrderInfo> paginationResult //
//				= orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
//
//		model.addAttribute("paginationResult", paginationResult);
//		return "orderList";
//	}
//
//	// GET: Hiển thị product
//	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
//	public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
//		ProductForm productForm = null;
//
//		if (code != null && code.length() > 0) {
//			Product product = productDAO.findProduct(code);
//			if (product != null) {
//				productForm = new ProductForm(product);
//			}
//		}
//		if (productForm == null) {
//			productForm = new ProductForm();
//			productForm.setNewProduct(true);
//		}
//		model.addAttribute("productForm", productForm);
//		return "product";
//	}
//
//	// POST: Save product
//	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
//	public String productSave(Model model, //
//			@ModelAttribute("productForm") @Validated ProductForm productForm, //
//			BindingResult result, //
//			final RedirectAttributes redirectAttributes) {
//
//		if (result.hasErrors()) {
//			return "product";
//		}
//		try {
//			productDAO.save(productForm);
//		} catch (Exception e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			String message = rootCause.getMessage();
//			model.addAttribute("errorMessage", message);
//			// Show product form.
//			return "product";
//		}
//
//		return "redirect:/productList";
//	}
//
//	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
//	public String orderView(Model model, @RequestParam("orderId") String orderId) {
//		OrderInfo orderInfo = null;
//		if (orderId != null) {
//			orderInfo = this.orderDAO.getOrderInfo(orderId);
//		}
//		if (orderInfo == null) {
//			return "redirect:/admin/orderList";
//		}
//		List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
//		orderInfo.setDetails(details);
//
//		model.addAttribute("orderInfo", orderInfo);
//
//		return "order";
//	}
//	
//	// xoa product
//	@RequestMapping(value = { "/admin/deleteProduct" }, method = RequestMethod.POST)
//    public String deleteProduct(@RequestParam("code") String code) {
//        productDAO.deleteProductByCode(code);
//        return "redirect:/productList";
//    }
//}
package com.digidinos.shopping.controller;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digidinos.shopping.entity.Product;
import com.digidinos.shopping.form.AccountForm;
import com.digidinos.shopping.form.ProductForm;
import com.digidinos.shopping.model.OrderDetailInfo;
import com.digidinos.shopping.model.OrderInfo;
import com.digidinos.shopping.pagination.PaginationResult;
import com.digidinos.shopping.serviceWithRepo.AccountService;
import com.digidinos.shopping.serviceWithRepo.OrderService;
import com.digidinos.shopping.serviceWithRepo.ProductService;
import com.digidinos.shopping.validator.ProductFormValidator;


@Controller
@Transactional 
public class AdminController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private ProductFormValidator productFormValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == ProductForm.class) {
			dataBinder.setValidator(productFormValidator); 
		}
	}

	// GET: Hiển thị trang login
	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = { "/admin/createAccount" }, method = RequestMethod.GET)
	public String createAccount(Model model) {
	    model.addAttribute("accountForm", new AccountForm());
	    return "signup"; 
	}
	
	// POST: Save account
	@RequestMapping(value = { "/admin/createAccount" }, method = RequestMethod.POST)
	public String saveAccount(Model model, //
	        @ModelAttribute("accountForm") @Validated AccountForm accountForm, //
	        BindingResult result, //
	        final RedirectAttributes redirectAttributes) {
		
		if (accountService.findAccountByGmail(accountForm.getGmail()) != null) {
	        result.rejectValue("gmail", "error.accountForm", "Email already exists");
	    }
		
		// Kiểm tra nếu tên đăng nhập đã tồn tại bằng cách sử dụng findAccount
	    if (accountService.findAccount(accountForm.getUserName()) != null) {
	        result.rejectValue("userName", "error.accountForm", "Username already exists");
	    }
		
	    // Kiểm tra mật khẩu và xác nhận mật khẩu
	    if (!accountForm.getPassword().equals(accountForm.getConfirmPassword())) {
	        result.rejectValue("confirmPassword", "error.accountForm", "Passwords do not match");
	    }

	    if (result.hasErrors()) {
	        return "signup"; // Quay lại trang nếu có lỗi
	    }

	    try {
	        accountService.saveAccount(accountForm); // Gọi phương thức lưu tài khoản từ AccountService
	        redirectAttributes.addFlashAttribute("message", "Account created successfully!"); // Thông báo thành công
	    } catch (Exception e) {
	        Throwable rootCause = ExceptionUtils.getRootCause(e);
	        String message = rootCause.getMessage();
	        model.addAttribute("errorMessage", message);
	        return "signup"; // Quay lại trang nếu có lỗi
	    }

	    return "redirect:/admin/login"; // Redirect đến danh sách tài khoản hoặc trang mong muốn
	}


	@RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getPassword());
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.isEnabled());

		model.addAttribute("userDetails", userDetails);
		return "accountInfo";
	}

	@RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
	public String orderList(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;

		PaginationResult<OrderInfo> paginationResult //
				= orderService.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

		model.addAttribute("paginationResult", paginationResult);
		return "orderList";
	}

	// GET: Hiển thị product
	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
	public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		ProductForm productForm = null;

		if (code != null && code.length() > 0) {
			Product product = productService.findProduct(code);
			if (product != null) {
				productForm = new ProductForm(product);
			}
		}
		if (productForm == null) {
			productForm = new ProductForm();
			productForm.setNewProduct(true);
		}
		model.addAttribute("productForm", productForm);
		return "product";
	}

	// POST: Save product
	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
	public String productSave(Model model, //
			@ModelAttribute("productForm") @Validated ProductForm productForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "product";
		}
		try {
			productService.save(productForm);
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			String message = rootCause.getMessage();
			model.addAttribute("errorMessage", message);
			// Show product form.
			return "product";
		}

		return "redirect:/productList";
	}

	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
	public String orderView(Model model, @RequestParam("orderId") String orderId) {
		OrderInfo orderInfo = null;
		if (orderId != null) {
			orderInfo = this.orderService.getOrderInfo(orderId);
		}
		if (orderInfo == null) {
			return "redirect:/admin/orderList";
		}
		List<OrderDetailInfo> details = this.orderService.listOrderDetailInfo(orderId);
		orderInfo.setDetails(details);

		model.addAttribute("orderInfo", orderInfo);

		return "order";
	}
	
	// xoa product
	@RequestMapping(value = { "/admin/deleteProduct" }, method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("code") String code) {
        productService.deleteProductByCode(code);
        return "redirect:/productList";
    }
	
	// quan ly trang thai don hang
	@PostMapping("/admin/order/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") String orderId,
                                    @RequestParam("orderStatus") String orderStatus) {
        orderService.updateOrderStatus(orderId, orderStatus);
        return "redirect:/admin/orderList";
    }
}