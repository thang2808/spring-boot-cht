package com.digidinos.shopping.serviceWithRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.digidinos.shopping.entity.Account;
import com.digidinos.shopping.form.AccountForm;
import com.digidinos.shopping.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public Account findAccount(String userName) {
		return accountRepository.findByUserName(userName);
	}
	
	// Tìm tài khoản theo email
    public Account findAccountByGmail(String gmail) {
        return accountRepository.findByGmail(gmail);
    }

	
	public void saveAccount(AccountForm accountForm) {
	    Account account = new Account();
	    account.setUserName(accountForm.getUserName());
	    account.setEncrytedPassword(passwordEncoder.encode(accountForm.getPassword())); // Mã hóa mật khẩu
	    account.setActive(accountForm.isActive());
	    account.setUserRole(Account.ROLE_USER); // Gán ROLE_USER làm mặc định
	    account.setGmail(accountForm.getGmail());

	    accountRepository.save(account);
	}
	
//	public void saveCustomerInfo(CustomerForm customerForm) {
//		CustomerInfo customerInfo = new CustomerInfo();
//		customerInfo.setName(customerForm.getName());
//		customerInfo.setEmail(customerForm.getEmail());
//		customerInfo.setPhone(customerForm.getPhone());
//		customerInfo.setAddress(customerForm.getAddress());
//	}
}
