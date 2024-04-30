package com.security.Security.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class TokenHandler {

    private static final String AES = "AES";
    private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    
    public static String decrypt(String encryptedText) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), AES);
    }
}
