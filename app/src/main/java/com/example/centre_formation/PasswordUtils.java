package com.example.centre_formation;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword).verified;
    }
}

