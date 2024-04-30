package com.security.SecondService.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class TokenHandler {
	private static final String AES = "AES";
	private static final String SECRET_KEY = "5367566B59703373"; // 16 bytes (128 bits)

	public static String encrypt(String plainText) throws Exception {
	    Key key = generateKey();
	    Cipher cipher = Cipher.getInstance(AES);
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	    return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	private static Key generateKey() {
	    byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
	    return new SecretKeySpec(keyBytes, AES);
	}
}