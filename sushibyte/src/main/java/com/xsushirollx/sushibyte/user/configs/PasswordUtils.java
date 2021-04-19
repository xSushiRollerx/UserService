package com.xsushirollx.sushibyte.user.configs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Sergey Kargopolov
 * copied from https://www.appsdeveloperblog.com/encrypt-user-password-example-java/
 */
public class PasswordUtils {
    public static String generateSecurePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
