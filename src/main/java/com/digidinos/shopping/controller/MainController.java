//package com.digidinos.shopping.controller;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.digidinos.shopping.dao.OrderDAO;
//import com.digidinos.shopping.dao.ProductDAO;
//import com.digidinos.shopping.entity.Product;
//import com.digidinos.shopping.form.CustomerForm;
//import com.digidinos.shopping.model.CartInfo;
//import com.digidinos.shopping.model.CustomerInfo;
//import com.digidinos.shopping.model.ProductInfo;
//import com.digidinos.shopping.pagination.*;
//import com.digidinos.shopping.utils.*;
//import com.digidinos.shopping.validator.CustomerFormValidator;
//
//@Controller
//@Transactional
//public class MainController {
//
//	@Autowired
//	private OrderDAO orderDAO;
//
//	@Autowired
//	private ProductDAO productDAO;
//
//	@Autowired
//	private CustomerFormValidator customerFormValidator;
//
//	@InitBinder
//	public void myInitBinder(WebDataBinder dataBinder) {
//		Object target = dataBinder.getTarget();
//		if (target == null) {
//			return;
//		}
//		System.out.println("Target=" + target);
//
//		// Trường hợp update SL trên giỏ hàng.
//		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
//		if (target.getClass() == CartInfo.class) {
//
//		}
//
//		// Trường hợp save thông tin khách hàng.
//		// (@ModelAttribute @Validated CustomerInfo customerForm)
//		else if (target.getClass() == CustomerForm.class) {
//			dataBinder.setValidator(customerFormValidator);
//		}
//
//	}
//
//	@RequestMapping("/403")
//	public String accessDenied() {
//		return "/403";
//	}
//
//	@RequestMapping("/")
//	public String home() {
//		return "index";
//	}
//
//	// Danh sách sản phẩm.
//	@RequestMapping({ "/productList" })
//	public String listProductHandler(Model model, //
//			@RequestParam(value = "name", defaultValue = "") String likeName,
//			@RequestParam(value = "page", defaultValue = "1") int page) {
//		final int maxResult = 5;
//		final int maxNavigationPage = 10;
//
//		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
//				maxResult, maxNavigationPage, likeName);
//
//		model.addAttribute("paginationProducts", result);
//		return "productList";
//	}
//
//	@RequestMapping({ "/buyProduct" })
//	public String listProductHandler(HttpServletRequest request, Model model, //
//			@RequestParam(value = "code", defaultValue = "") String code) {
//
//		Product product = null;
//		if (code != null && code.length() > 0) {
//			product = productDAO.findProduct(code);
//		}
//		if (product != null) {
//
//			// 
//			CartInfo cartInfo = Utils.getCartInSession(request);
//
//			ProductInfo productInfo = new ProductInfo(product);
//
//			cartInfo.addProduct(productInfo, 1);
//		}
//
//		return "redirect:/shoppingCart";
//	}
//
//	@RequestMapping({ "/shoppingCartRemoveProduct" })
//	public String removeProductHandler(HttpServletRequest request, Model model, //
//			@RequestParam(value = "code", defaultValue = "") String code) {
//		Product product = null;
//		if (code != null && code.length() > 0) {
//			product = productDAO.findProduct(code);
//		}
//		if (product != null) {
//
//			CartInfo cartInfo = Utils.getCartInSession(request);
//
//			ProductInfo productInfo = new ProductInfo(product);
//
//			cartInfo.removeProduct(productInfo);
//
//		}
//
//		return "redirect:/shoppingCart";
//	}
//
//	// POST: Cập nhập số lượng cho các sản phẩm đã mua.
//	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
//	public String shoppingCartUpdateQty(HttpServletRequest request, //
//			Model model, //
//			@ModelAttribute("cartForm") CartInfo cartForm) {
//
//		CartInfo cartInfo = Utils.getCartInSession(request);
//		cartInfo.updateQuantity(cartForm);
//
//		return "redirect:/shoppingCart";
//	}
//
//	// GET: Hiển thị giỏ hàng.
//	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
//	public String shoppingCartHandler(HttpServletRequest request, Model model) {
//		CartInfo myCart = Utils.getCartInSession(request);
//
//		model.addAttribute("cartForm", myCart);
//		return "shoppingCart";
//	}
//
//	// GET: Nhập thông tin khách hàng.
//	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
//	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
//
//		CartInfo cartInfo = Utils.getCartInSession(request);
//
//		if (cartInfo.isEmpty()) {
//
//			return "redirect:/shoppingCart";
//		}
//		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
//
//		CustomerForm customerForm = new CustomerForm(customerInfo);
//
//		model.addAttribute("customerForm", customerForm);
//
//		return "shoppingCartCustomer";
//	}
//
//	// POST: Save thông tin khách hàng.
//	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
//	public String shoppingCartCustomerSave(HttpServletRequest request, //
//			Model model, //
//			@ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
//			BindingResult result, //
//			final RedirectAttributes redirectAttributes) {
//
//		if (result.hasErrors()) {
//			customerForm.setValid(false);
//			// Forward tới trang nhập lại.
//			return "shoppingCartCustomer";
//		}
//
//		customerForm.setValid(true);
//		CartInfo cartInfo = Utils.getCartInSession(request);
//		CustomerInfo customerInfo = new CustomerInfo(customerForm);
//		cartInfo.setCustomerInfo(customerInfo);
//
//		return "redirect:/shoppingCartConfirmation";
//	}
//
//	// GET: Xem lại thông tin để xác nhận.
//	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
//	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
//		CartInfo cartInfo = Utils.getCartInSession(request);
//
//		if (cartInfo == null || cartInfo.isEmpty()) {
//
//			return "redirect:/shoppingCart";
//		} else if (!cartInfo.isValidCustomer()) {
//
//			return "redirect:/shoppingCartCustomer";
//		}
//		model.addAttribute("myCart", cartInfo);
//
//		return "shoppingCartConfirmation";
//	}
//
//	// POST: Gửi đơn hàng (Save).
//	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
//
//	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
//		CartInfo cartInfo = Utils.getCartInSession(request);
//
//		if (cartInfo.isEmpty()) {
//
//			return "redirect:/shoppingCart";
//		} else if (!cartInfo.isValidCustomer()) {
//
//			return "redirect:/shoppingCartCustomer";
//		}
//		try {
//			orderDAO.saveOrder(cartInfo);
//		} catch (Exception e) {
//
//			return "shoppingCartConfirmation";
//		}
//
//		// Xóa giỏ hàng khỏi session.
//		Utils.removeCartInSession(request);
//
//		// Lưu thông tin đơn hàng cuối đã xác nhận mua.
//		Utils.storeLastOrderedCartInSession(request, cartInfo);
//
//		return "redirect:/shoppingCartFinalize";
//	}
//
//	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
//	public String shoppingCartFinalize(HttpServletRequest request, Model model) {
//
//		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
//
//		if (lastOrderedCart == null) {
//			return "redirect:/shoppingCart";
//		}
//		model.addAttribute("lastOrderedCart", lastOrderedCart);
//		return "shoppingCartFinalize";
//	}
//
//	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
//	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
//			@RequestParam("code") String code) throws IOException {
//		Product product = null;
//		if (code != null) {
//			product = this.productDAO.findProduct(code);
//		}
//		if (product != null && product.getImage() != null) {
//			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//			response.getOutputStream().write(product.getImage());
//		}
//		response.getOutputStream().close();
//	}
//
//}

package com.digidinos.shopping.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digidinos.shopping.entity.Account;
import com.digidinos.shopping.entity.Comment;
import com.digidinos.shopping.entity.Product;
import com.digidinos.shopping.form.CustomerForm;
import com.digidinos.shopping.model.CartInfo;
import com.digidinos.shopping.model.CartLineInfo;
import com.digidinos.shopping.model.CustomerInfo;
import com.digidinos.shopping.model.ProductInfo;
import com.digidinos.shopping.pagination.*;
import com.digidinos.shopping.serviceWithRepo.AccountService;
import com.digidinos.shopping.serviceWithRepo.CommentService;
import com.digidinos.shopping.serviceWithRepo.OrderService;
import com.digidinos.shopping.serviceWithRepo.ProductService;
import com.digidinos.shopping.utils.*;
import com.digidinos.shopping.validator.CustomerFormValidator;

@Controller
@Transactional
public class MainController {
	
	@Autowired 
	private AccountService accountService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;
	
	@Autowired
    private CommentService commentService;

	@Autowired
	private CustomerFormValidator customerFormValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		// Trường hợp update SL trên giỏ hàng.
		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if (target.getClass() == CartInfo.class) {

		}

		// Trường hợp save thông tin khách hàng.
		// (@ModelAttribute @Validated CustomerInfo customerForm)
		else if (target.getClass() == CustomerForm.class) {
			dataBinder.setValidator(customerFormValidator);
		}

	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	// Danh sách sản phẩm.
	@RequestMapping({ "/productList" })
	public String listProductHandler(Model model, //
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 5;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productService.queryProducts(page, //
				maxResult, maxNavigationPage, likeName);

		model.addAttribute("paginationProducts", result);
		return "productList";
	}

	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "code", defaultValue = "") String code) {

		Product product = null;
		if (code != null && code.length() > 0) {
			product = productService.findProduct(code);
		}
		if (product != null) {

			// 
			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.addProduct(productInfo, 1);
		}

		return "redirect:/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "code", defaultValue = "") String code) {
		Product product = null;
		if (code != null && code.length() > 0) {
			product = productService.findProduct(code);
		}
		if (product != null) {

			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.removeProduct(productInfo);

		}

		return "redirect:/shoppingCart";
	}

	// POST: Cập nhập số lượng cho các sản phẩm đã mua.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("cartForm") CartInfo cartForm) {
		// lay thong tin trong gio hang
		CartInfo cartInfo = Utils.getCartInSession(request);
		// Kiểm tra số lượng từng sản phẩm
	    for (CartLineInfo cartLine : cartForm.getCartLines()) {
	        ProductInfo productInfo = productService.findProductInfo(cartLine.getProductInfo().getCode());
	        if (productInfo != null) {
	            int remainingStock = productInfo.getRepo(); // Số lượng còn trong kho
	            int quantityToBuy = cartLine.getQuantity(); // Số lượng người dùng muốn mua
	            
	            // Kiểm tra điều kiện số lượng
	            if (quantityToBuy > remainingStock || quantityToBuy <= 0) {
	                model.addAttribute("errorMessage", "Quantity must be less than or equal to stock for product: " 
	                                                  + productInfo.getName());
	                model.addAttribute("cartForm", cartForm); // Hiển thị lại thông tin giỏ hàng
	                return "shoppingCart"; // Trả về trang giỏ hàng với thông báo lỗi
	            }
	        }
	    }
	    
	    
		cartInfo.updateQuantity(cartForm);

		return "redirect:/shoppingCart";
	}



	// GET: Hiển thị giỏ hàng.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);

		model.addAttribute("cartForm", myCart);
		return "shoppingCart";
	}

	// GET: Nhập thông tin khách hàng.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		}
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		CustomerForm customerForm = new CustomerForm(customerInfo);
		
		// Lấy thông tin tài khoản đã đăng nhập
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()) {
	        String userName = authentication.getName();
	        Account account = accountService.findAccount(userName);

	        if (account != null) {
	            // Nếu đã đăng nhập và có tài khoản, sử dụng email từ tài khoản đó
	            customerForm.setEmail(account.getGmail());
	        }
	    }

		model.addAttribute("customerForm", customerForm);

		return "shoppingCartCustomer";
	}

	// POST: Save thông tin khách hàng.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			customerForm.setValid(false);
			// Forward tới trang nhập lại.
			return "shoppingCartCustomer";
		}

		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);
		CustomerInfo customerInfo = new CustomerInfo(customerForm);
		cartInfo.setCustomerInfo(customerInfo);

		return "redirect:/shoppingCartConfirmation";
	}

	// GET: Xem lại thông tin để xác nhận.
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo == null || cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			return "redirect:/shoppingCartCustomer";
		}
		model.addAttribute("myCart", cartInfo);

		return "shoppingCartConfirmation";
	}

	// POST: Gửi đơn hàng (Save)
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
	    CartInfo cartInfo = Utils.getCartInSession(request);

	    if (cartInfo.isEmpty()) {
	        return "redirect:/shoppingCart";
	    } else if (!cartInfo.isValidCustomer()) {
	        return "redirect:/shoppingCartCustomer";
	    }
	    
	    // Ghi lại đơn hàng và cập nhật số lượng trong kho
	    try {
	        orderService.saveOrder(cartInfo);

	        // Cập nhật số lượng trong kho cho mỗi sản phẩm trong đơn hàng
	        for (CartLineInfo cartLine : cartInfo.getCartLines()) {
	            Product product = productService.findProduct(cartLine.getProductInfo().getCode());
	            if (product != null) {
	                int newStock = product.getRepo() - cartLine.getQuantity();
	                product.setRepo(newStock);
	                productService.saveProduct(product); // Lưu thay đổi vào database
	            }
	        }
	    } catch (Exception e) {
	        return "shoppingCartConfirmation";
	    }

	    // Xóa giỏ hàng khỏi session.
	    Utils.removeCartInSession(request);
	    
	    // Lưu thông tin đơn hàng cuối đã xác nhận mua.
	    Utils.storeLastOrderedCartInSession(request, cartInfo);

	    return "redirect:/shoppingCartFinalize";
	}


	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

		if (lastOrderedCart == null) {
			return "redirect:/shoppingCart";
		}
		model.addAttribute("lastOrderedCart", lastOrderedCart);
		return "shoppingCartFinalize";
	}

	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("code") String code) throws IOException {
		Product product = null;
		if (code != null) {
			product = this.productService.findProduct(code);
		}
		if (product != null && product.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}
 
	// tim kiem san pham
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchProductHandler(Model model, @RequestParam("keyword") String keyword,
                                       @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = productService.queryProducts(page, maxResult, maxNavigationPage, keyword);

        // Đưa kết quả tìm kiếm vào model
        model.addAttribute("paginationProducts", result);
        model.addAttribute("keyword", keyword); 

        return "productList";
    }
	
	// hien thi thong tin san pham khi click vao
			@RequestMapping(value = { "/productDetail" }, method = RequestMethod.GET)
			public String productDetail(@RequestParam("code") String code, Model model) {
			    // Tìm thông tin sản phẩm theo mã sản phẩm
			    ProductInfo productInfo = productService.findProductInfo(code);
			    
			    // Kiểm tra nếu sản phẩm không tồn tại
			    if (productInfo == null) {
			        return "redirect:/productList"; // Chuyển hướng về danh sách sản phẩm
			    }
			    
			    // Thêm thông tin sản phẩm vào model để sử dụng trong view
			    model.addAttribute("productInfo", productInfo);
			    
			    // Lấy danh sách bình luận cho sản phẩm
			    List<Comment> comments = commentService.findCommentsByProductCode(code);
			    model.addAttribute("comments", comments); // Thêm danh sách bình luận vào model
			    
			    // Trả về view cho chi tiết sản phẩm
			    return "productDetail";
			}
			
			@RequestMapping(value = { "/products/{code}/comment" }, method = RequestMethod.POST)
			public String addComment(@PathVariable("code") String code, 
			                         @ModelAttribute("newComment") Comment newComment, 
			                         HttpServletRequest request, 
			                         RedirectAttributes redirectAttributes) {
			    Product product = productService.findProduct(code);
			    
			    // Lấy thông tin tài khoản đang đăng nhập
			    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			    if (authentication != null && authentication.isAuthenticated()) {
			        String userName = authentication.getName(); // Tên đăng nhập
			        Account account = accountService.findAccount(userName); // Lấy tài khoản từ dịch vụ
			        
			        if (account != null) {
			            newComment.setName(account.getUserName()); // Giả sử bạn có phương thức getUserName()
			            newComment.setEmail(account.getGmail()); // Giả sử bạn đã lưu email trong account
			            newComment.setCreatedAt(new Date()); // cap nhat thoi gian cmt
			        }
			    }
			    
			    if (product != null) {
			        newComment.setProduct(product);
			        // Lưu comment vào cơ sở dữ liệu
			        commentService.saveComment(newComment);
			        redirectAttributes.addFlashAttribute("message", "Bình luận của bạn đã được thêm thành công.");
			    } 
			    return "redirect:/productDetail?code=" + code;
			}

	
}

