package com.titan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtils {
	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = "ADBSJHJS12547896".getBytes();

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public PasswordUtils() throws Exception {
		myEncryptionKey = "ThisIsSpartaThisIsSparta";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public static String encryptPassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	private static String secretKey = "boooooooooom!!!!";
	private static String salt = "ssshhhhhhhhhhh!!!!";

	/*
	 * public static String encrypt(String strToEncrypt, String secret) { try {
	 * byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	 * IvParameterSpec ivspec = new IvParameterSpec(iv);
	 * 
	 * SecretKeyFactory factory =
	 * SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); KeySpec spec = new
	 * PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256); SecretKey
	 * tmp = factory.generateSecret(spec); SecretKeySpec secretKey = new
	 * SecretKeySpec(tmp.getEncoded(), "AES");
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5");
	 * cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec); return
	 * Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(
	 * "UTF-8"))); } catch (Exception e) {
	 * System.out.println("Error while encrypting: " + e.toString()); } return null;
	 * }
	 * 
	 * 
	 * public static String decrypt(String strToDecrypt, String secret) { try {
	 * byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	 * IvParameterSpec ivspec = new IvParameterSpec(iv);
	 * 
	 * SecretKeyFactory factory =
	 * SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); KeySpec spec = new
	 * PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256); SecretKey
	 * tmp = factory.generateSecret(spec); SecretKeySpec secretKey = new
	 * SecretKeySpec(tmp.getEncoded(), "AES");
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
	 * cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec); return new
	 * String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt))); } catch
	 * (Exception e) { System.out.println("Error while decrypting: " +
	 * e.toString()); } return null; }
	 * 
	 *//*
		 * public static String encrypt(String valueToEnc) throws Exception {
		 * 
		 * Key key = generateKey(); Cipher c = Cipher.getInstance(ALGORITHM);
		 * c.init(Cipher.ENCRYPT_MODE, key);
		 * 
		 * System.out.println("valueToEnc.getBytes().length "+valueToEnc.getBytes().
		 * length); byte[] encValue = c.doFinal(valueToEnc.getBytes());
		 * System.out.println("encValue length" + encValue.length); byte[]
		 * encryptedByteValue = new Base64().encode(encValue);
		 * System.out.println("encryptedByteValue: " +encryptedByteValue); String
		 * encryptedValue = encryptedByteValue.toString();
		 * System.out.println("encryptedValue " + encryptedValue);
		 * 
		 * return encryptedValue; }
		 * 
		 * public static String decrypt(String encryptedValue) throws Exception { Key
		 * key = generateKey(); Cipher c = Cipher.getInstance(ALGORITHM);
		 * c.init(Cipher.DECRYPT_MODE, key);
		 * 
		 * byte[] enctVal = c.doFinal(encryptedValue.getBytes());
		 * System.out.println("enctVal length " + enctVal.length);
		 * 
		 * byte[] decordedValue = new Base64().decode(enctVal);
		 * 
		 * return decordedValue.toString(); }
		 * 
		 * private static Key generateKey() throws Exception { Key key = new
		 * SecretKeySpec(keyValue, ALGORITHM); return key; }
		 */
	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

}
