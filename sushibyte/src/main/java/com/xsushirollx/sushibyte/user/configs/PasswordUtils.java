package com.xsushirollx.sushibyte.user.configs;

/**
 * @author Sergey Kargopolov
 * copied from https://www.appsdeveloperblog.com/encrypt-user-password-example-java/
 */
public class PasswordUtils {
    public static String generateSecurePassword(String password) {
        return ""+(13+password.hashCode());
    }
    
}
