package com.projectunitato.encrypchat;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;


public class Encriptor {
	
	
	private String AESkey;
	
	
	
	public Encriptor(String AESKey)
	{
		AESkey = AESKey;
		
	}
	
	
	
	
	
	//testing function
	public static void Run()
	{
		String str = "Hello World";
		String tmpKey = "123qweasdzxc!@#$";
		byte[] encryptedMsg = aesEncrypt(tmpKey, str);
	    String msgString = byte2hex(encryptedMsg);
	    byte[] decryptedMsg = null;
	    String end = null;
	    try {
			decryptedMsg = aesDecrypt(tmpKey, hex2byte(msgString.getBytes()));
			end = new String(decryptedMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	
	
	/*
	 * 
	 * Encriptor enc = new Encriptor(sharedprefs.getPref("EASkey" + chatId);
	 * 
	 * MessageReceivedListener()
	 * {
	 * 		OnReceive(String Message)
	 * 		{
	 * 			enc.Decrypt(message);
	 * 			textview.settext(this^);
	 * 		}
	 * }
	 * 
	 * 
	 * NewMessage(String number)
	 * {
	 * 		KeyPair kp = Encriptor.GenerateKeyPair();
	 * }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	 // utility function
	 public static String byte2hex(byte[] b) 
	 {
		 String hs = "";
		 String stmp = "";
		 for (int n = 0; n < b.length; n++) 
		 {
			 stmp = Integer.toHexString(b[n] & 0xFF);
			 if (stmp.length() == 1)
				 hs += ("0" + stmp);
			 else
				 hs += stmp;
		 }
		 return hs.toUpperCase();
	 }
	 
	 
	 // utility function: convert hex array to byte array
	 public static byte[] hex2byte(byte[] b) 
	 {
		 if ((b.length % 2) != 0)
			 throw new IllegalArgumentException("hello");
	
		 byte[] b2 = new byte[b.length / 2];
	
		 for (int n = 0; n < b.length; n += 2) 
		 {
			 String item = new String(b, n, 2);
			 b2[n / 2] = (byte) Integer.parseInt(item, 16);
		 }
		 return b2;
	 }
	 
	 
	 
	 

	 // encryption function
	 public static byte[] aesEncrypt(String keyString, String contentString) {
	
		 try 
		 {
			 byte[] returnArray;
		
			 // generate AES secret key from user input
			 Key key = generateKey(keyString);
		
			 // specify the cipher algorithm using AES
			 Cipher c = Cipher.getInstance("AES");
		
			 // specify the encryption mode
			 c.init(Cipher.ENCRYPT_MODE, key);
		
			 // encrypt
			 returnArray = c.doFinal(contentString.getBytes());
		
			 return returnArray;
	
		 } catch (Exception e) {
			 e.printStackTrace();
			 byte[] returnArray = null;
			 return returnArray;
		 }
	
		 }
	
	
	
	 // decryption function
	 public static byte[] aesDecrypt(String secretKeyString, byte[] encryptedMsg) throws Exception {
	
		 // generate AES key from the user input secret key
		 Key key = generateKey(secretKeyString);
	
		 // get the cipher algorithm for AES
		 Cipher c = Cipher.getInstance("AES");
	
		 // specify the decryption mode
		 c.init(Cipher.DECRYPT_MODE, key);
	
		 // decrypt the message
		 byte[] decValue = c.doFinal(encryptedMsg);
	
		 return decValue;
	 }
	
	
	
	 private static Key generateKey(String secretKeyString) throws Exception {
		 // generate AES key from string
		 Key key = new SecretKeySpec(secretKeyString.getBytes(), "AES");
		 return key;
	 }
	
	
	
	
	
	
	
	
	
	
	public static String RsaEncript(String message, KeyPair kp)
	{
		String text = null;
		try{
			
	 
	        PublicKey publicKey = kp.getPublic();
	        PrivateKey privateKey = kp.getPrivate();
	 
	        text = message;
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] x = cipher.doFinal(text.getBytes());
	 
	        text = getStr(x);
	        
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return text;
	}
	
	
	public static KeyPair genKeyPair()
	{
		
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(256);
	        KeyPair kp = keyGen.genKeyPair();
	        return kp;
		}catch(Exception e){
			e.printStackTrace();
		}
			return null;
	}
	
	
	
	public static String RsaDecript(String message, KeyPair kp)
	{
		Log.d("Cipher", "Decript in: " + message);
		String text = null;
		try{
			PublicKey publicKey = kp.getPublic();
	        PrivateKey privateKey = kp.getPrivate();
	 
	        text = message;
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] y = cipher.doFinal(getByte(message));
	        
	        text = new String(y);
		}catch(Exception e)
		{
		
		}
		return text;
	}
	
	private static String getStr(byte[] in)
	{
		String str = "";
		for(int i = 0; i < in.length; i++)
			str += (char) in[i];
		return str;
	}
	
	private static byte[] getByte(String str)
	{
		byte[] b = new byte[str.length()];
		for(int i = 0; i < str.length(); i++)
			b[i] = (byte) str.charAt(i);
		return b;
	}
	
	
	
	
	
}
