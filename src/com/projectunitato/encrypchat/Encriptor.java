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

import android.util.Log;


public class Encriptor {
	
	public static String Encript(String message, KeyPair kp)
	{
		/*
		 * TODO
		 * USE new String(Byte[]) instead of ToString!!!!
		 */
		String text = null;
		try{
			/*
			Log.d("Cipher", "Start msg: " + message);
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, kp.getPublic());
	        
	        text = getStr(cipher.doFinal(getByte(message)));
	        Log.i("Cipher", "Encrypted msg: " + text);
	        
	        cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());
			text = new String(cipher.doFinal(text.getBytes()), Charset.forName("UTF-8"));
	        
			Log.w("Cipher", "Decrypted msg: " + text);
			
			
			/*
			for(byte i = -127; i < 127; i++)
				Log.d("strings", i + "      " + new String(new byte[]{i}, Charset.forName("UTF-8")));
	 
	        PublicKey publicKey = kp.getPublic();
	        PrivateKey privateKey = kp.getPrivate();
	 
	        
	        text = message;
	        
	        
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] x = cipher.doFinal(text.getBytes());
	        
	        String xs = getStr(x);
	        byte[] xsb = getByte(xs);
	        
	        
	        Log.w("Cipher", x.toString());
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] y = cipher.doFinal(x);
	        Log.w("Cipher", new String(y));
	        text = new String(y);
			*/
			
			
	 
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
	
	
	public static KeyPair genKey()
	{
		
		try{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
        KeyPair kp = keyGen.genKeyPair();
        return kp;
		}catch(Exception e){
		
		}
		return null;
	}
	
	
	
	public static String Decript(String message, KeyPair kp)
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
