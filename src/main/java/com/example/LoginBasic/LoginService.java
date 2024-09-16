package com.example.LoginBasic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
    private static final String url = "jdbc:postgresql://10.1.1.102:5432/production_test?currentSchema=db_hungha2";
    private static final String username = "dev_3";
    private static final String password = "dev3_0809@saigonbpo";

    private DataSource dataSource;

    public LoginService() {
        this.dataSource = createDataSource();
    }

    private DataSource createDataSource() {
    	
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
        
    }
    
    public boolean checkLogin(String username, String password) {
    	
        String query = "SELECT COUNT(*) FROM user_account WHERE username = ? AND userpassword = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                	
                    int count = resultSet.getInt(1);
                    return count > 0;
                    
                }
            }
            
        } catch (Exception e) {
            
        	e.printStackTrace();
        
        }
        
        return false;
    
    }
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    public void mailLogin(String to, String subject, String content) {
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("non-reply@saigonbpo.vn");
    	message.setTo(to);
    	message.setSubject(subject);
    	message.setText(content);
    	
    	javaMailSender.send(message);
    	
    }
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Scheduled(fixedRate = 5000)
    public void mailAutoSendAfterPeriod() {
    	String currentDateTime = LocalDateTime.now().format(formatter);
    	mailLogin("hung.ha2@saigonbpo.vn", "Test Scheduled Email", "Current time is: " + currentDateTime);
    	
    }
    
    @Scheduled(cron = "0 5 11 * * ?")
    public void mailAutoSendAtTime() {
    	String currentDateTime = LocalDateTime.now().format(formatter);
    	mailLogin("hung.ha2@saigonbpo.vn", "Test Scheduled Email", "Current time is: " + currentDateTime);
    	
    }
}