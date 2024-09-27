package com.digidinos.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digidinos.shopping.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	Account findByUserName(String userName);

	Account findByGmail(String Gmail);
}