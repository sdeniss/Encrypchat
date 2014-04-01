package com.projectunitato.encrypchat;

import android.util.Log;


public class Encriptor {
	
	
	String EncriptedMessage;
	String OriginalMessage;
	
	public Encriptor(String message)
	{
		//TODO http://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml
		OriginalMessage = message;
		EncriptedMessage = "";
		char c = 'a' + 1;
		Log.i("mINFO", "ch is " + c);
		for(int i = 0; i < message.length(); i++)
			EncriptedMessage += (char) (OriginalMessage.charAt(i) + 1);
	}
}
