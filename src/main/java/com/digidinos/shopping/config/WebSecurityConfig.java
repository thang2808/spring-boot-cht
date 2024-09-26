package com.digidinos.shopping.config;

import com.digidinos.shopping.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    UserDetailsServiceImpl userDetailsService;

    // ma hoa mat khau
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
    //cau hinh, phan quyen va ma hoa mat khau
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // Các yêu cầu phải login với vai trò ROLE_EMPLOYEE hoặc ROLE_MANAGER.
        // Nếu chưa login, nó sẽ redirect tới trang /admin/login.
        http.authorizeRequests()
        .antMatchers("/admin/createAccount").permitAll()
        .antMatchers("/admin/order","/admin/orderList").access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");

        
        http.authorizeRequests()
        .antMatchers("/admin/accountInfo").access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER') or hasAuthority('USER')");

        // Các trang chỉ dành cho MANAGER
        http.authorizeRequests().antMatchers("/admin/product", "/admin/deleteProduct").access("hasRole('ROLE_MANAGER')");
        // Các trang chỉ dành cho USER
        http.authorizeRequests().antMatchers("/admin/orderListForUser").access("hasAuthority('USER')");
        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/admin/login")//
                .defaultSuccessUrl("/admin/accountInfo")//
                .failureUrl("/admin/login?error=true")//
                .usernameParameter("userName")//
                .passwordParameter("password")

                // Cấu hình cho trang Logout.
                // (Sau khi logout, chuyển tới trang home)
                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");

    }
}