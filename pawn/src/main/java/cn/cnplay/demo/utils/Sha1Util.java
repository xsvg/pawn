package cn.cnplay.demo.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;

public class Sha1Util {
	
	private final static SecureRandom random = new SecureRandom();
	private final static int ITERATION = 1024;
	
	public static byte[] generateSalt() {
		byte[] bytes = new byte[8];
		random.nextBytes(bytes);
		return bytes;
	}

	public static String encrypt(String input, String saltHex){
		return encrypt(input,saltHex,ITERATION);
	}
	
	public static String encrypt(String input, String saltHex,int iterations){
		try{
			byte[] salt = Hex.decodeHex(saltHex.toCharArray());
			byte[] encrypt =  encrypt(input.getBytes(),salt,iterations);
			return Hex.encodeHexString(encrypt);
		}catch(Exception e){
			throw new RuntimeException("加密失败");
		}
	}
	
	public static byte[] encrypt(byte[] input, byte[] salt){
		return encrypt(input,salt,ITERATION);
	}
	
	public static byte[]  encrypt(byte[] input, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			if (salt != null) {
				digest.update(salt);
			}
			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}


}
