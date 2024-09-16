package com.example.LoginBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private String email = "hung.ha2@saigonbpo.vn";
	private String emailSubject = "Test SMTP";
	private String emailContent = "Email Sent";
	
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
    	
        if (loginService.checkLogin(username, password)) {
        	
        	loginService.mailLogin(email, emailSubject, emailContent);
        	loginService.mailAutoSendAfterPeriod();
        	loginService.mailAutoSendAtTime();
            return "Back-end Login Successful";
            
        } else {
        	
            return "Back-end Login Failed";
            
        }
    }
}