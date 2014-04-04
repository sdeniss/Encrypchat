package com.projectunitato.encrypchat;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;


public class Encriptor {
	
	
	private String AESkey;
	
	
	
	public Encriptor(String AESKey)
	{
		AESkey = AESKey;
	}
	
	public String Decrypt(String message)
	{
		String decryptedMsg = null;
		return decryptedMsg;
	}
	
	public static String Decrypt(String message, Key AESkey)
	{
		return null;
	}
	
	
	
	public static void SampleTransmissionStart()
	{
		//=======ALICE======
		KeyPair kp = GenerateRSAKeyPair(); 
		PublicKey publicKey = kp.getPublic();
		//SEND publicKey
		
		//========BOB=======
		String aesKey = GenerateAESKeyString();
		String encryptedAesKey = RSAEncript(aesKey, publicKey);
		//SEND encryptedAesKey
		
		
		//=======ALICE======
		String decryptedAesKey = RSADecript(encryptedAesKey, kp.getPrivate());
		String message = "HELLO WORLD";
		String toSend = AESEncrypt(decryptedAesKey, message);
		//SEND toSend
		
		
		//========BOB=======		
		String decryptedMsg = "";
		try {
			decryptedMsg = AESDecrypt(aesKey, toSend);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("strings", decryptedMsg);
	}
	
	
	
	
	
	static public String GenerateAESKeyString()
	{
		Random r = new Random();
		String keyString = "";
		for(int i = 0; i < 16; i++){
			if(r.nextBoolean())
				keyString += (char) r.nextInt(128);
			else
				keyString += (char) r.nextInt(128);
		}
		return keyString;
	}
	
	//testing function
	public static void TestRun()
	{
		String str = "Hello World";
		String tmpKey = GenerateAESKeyString();//"123qweasdzxc!@#$";
	    String msgString = AESEncrypt(tmpKey, str);
	    String end = null;
	    try {
	    	/* 	//OLD SYNTAX:
			decryptedMsg = AESDecrypt(tmpKey, hex2byte(msgString.getBytes()));
			end = new String(decryptedMsg);
			*/
	    	
	    	end = AESDecrypt(tmpKey, msgString);
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
	 public static String AESEncrypt(String keyString, String contentString) {
	
		 try 
		 {
			 byte[] returnArray;
		
			 
			 
			 // generate AES secret key from user input
			 Key key = AESKeyFromString(keyString);
		
			 // specify the cipher algorithm using AES
			 Cipher c = Cipher.getInstance("AES");
		
			 // specify the encryption mode
			 c.init(Cipher.ENCRYPT_MODE, key);
		
			 // encrypt
			 returnArray = c.doFinal(contentString.getBytes());
		
			 return byte2hex(returnArray);
	
		 } catch (Exception e) {
			 e.printStackTrace();
			 byte[] returnArray = null;
			 return byte2hex(returnArray);
		 }
	
		 }
	
	
	
	 // decryption function
	 public static String AESDecrypt(String secretKeyString, String encryptedMsgString) throws Exception {
		 byte[] encryptedMsg = hex2byte(encryptedMsgString.getBytes());;
		 
		 // generate AES key from the user input secret key
		 Key key = AESKeyFromString(secretKeyString);
	
		 // get the cipher algorithm for AES
		 Cipher c = Cipher.getInstance("AES");
	
		 // specify the decryption mode
		 c.init(Cipher.DECRYPT_MODE, key);
	
		 // decrypt the message
		 byte[] decValue = c.doFinal(encryptedMsg);
		 
		 String decStr = new String(decValue);
		 return decStr;
	 }
	
	
	
	 private static Key AESKeyFromString(String secretKeyString) throws Exception {
		 // generate AES key from string
		 Key key = new SecretKeySpec(secretKeyString.getBytes(), "AES");
		 return key;
	 }
	
	
	
	
	
	
	
	
	
	
	public static String RSAEncript(String message, PublicKey publicKey)
	{
		String text = null;
		try{
			
	 
	 
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
	
	
	public static KeyPair GenerateRSAKeyPair()
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
	
	
	
	public static String RSADecript(String message, PrivateKey privateKey)
	{
		Log.d("Cipher", "Decript in: " + message);
		String text = null;
		try{
	 
	        text = message;
	        Cipher cipher = Cipher.getInstance("RSA");
	        
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
