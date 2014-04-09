package com.projectunitato.encrypchat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	
	ToggleButton showKeyToggle;
	EditText messageEt, keyEt;
	Button btnEncrypt, btnDecrypt;
	String KEY_PRIVATE_KEY = "rsa_private";
	String KEY_AES_KEY = "aes_key";
	String PREF_NAME = "default";
	Encriptor encriptor;
	SharedPreferences prefs;
	CheckBox sendSmsCheckbox, copyCheckBox;
	ScrollView scrollView;
	static ClipboardManager clipboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Encriptor.SampleTransmissionStart();
		
		prefs = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	    String aespref = prefs.getString(KEY_AES_KEY, "");
	    if(aespref.equals(""))
	    {
	    	encriptor = new Encriptor();
	    	aespref = encriptor.getAesKey();
	    	prefs.edit().putString(KEY_AES_KEY, aespref).apply();
	    }
	    else
	    	encriptor = new Encriptor(aespref);
	 
	    /*
	    
	    if (key.equals("")) {
	        // generate KeyPair
	        kp = Encriptor.GenerateRSAKeyPair();
	        ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o;
	        try {
	            o = new ObjectOutputStream(b);
	            o.writeObject(kp);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        byte[] res = b.toByteArray();
	        String encodedKey = Base64.encodeToString(res, Base64.DEFAULT);
	
	        prefs.edit().putString(PRIVATE_KEY_KEY, encodedKey).commit();
	
	    } else {
	        // read the KeyPair from internal storage
	        byte[] res = Base64.decode(key, Base64.DEFAULT);
	        ByteArrayInputStream bi = new ByteArrayInputStream(res);
	        ObjectInputStream oi;
	        try {
	            oi = new ObjectInputStream(bi);
	            Object obj = oi.readObject();
	            kp = (KeyPair) obj;
	            Log.w("Cipher", ((KeyPair) obj).toString());
	        } catch (Exception e)
	        {
	        
	        }
	    }

		*/
		
		
		keyEt = (EditText) findViewById(R.id.et_key);
	    messageEt = (EditText) findViewById(R.id.et_message);
		btnEncrypt = (Button) findViewById(R.id.button_encrypt);
		btnDecrypt = (Button) findViewById(R.id.button_decrypt);
		showKeyToggle = (ToggleButton) findViewById(R.id.toggle_show_key);
		clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
		sendSmsCheckbox = (CheckBox) findViewById(R.id.checkbox_sendSms);
		copyCheckBox = (CheckBox) findViewById(R.id.checkbox_copy);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		
		btnEncrypt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
		btnDecrypt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
		messageEt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
		sendSmsCheckbox.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf"));
		copyCheckBox.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf"));
	
		
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
			copyCheckBox.setVisibility(View.INVISIBLE);
		
		keyEt.setText(aespref);
		
		
		btnEncrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:"));
				sendIntent.putExtra("sms_body", encriptor.AESEncrypt(et.getText().toString()));
				startActivity(sendIntent);
				
				*/
				String key = keyEt.getText().toString();
				while(key.length() < 16)
					key += 'x';
				encriptor.setAESKey(key);
				prefs.edit().putString(KEY_AES_KEY, keyEt.getText().toString()).apply();
				messageEt.setText(encriptor.AESEncrypt(messageEt.getText().toString()));
			
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				if(sendSmsCheckbox.isChecked())
				{
					Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					sendIntent.setData(Uri.parse("sms:"));
					sendIntent.putExtra("sms_body", encriptor.AESEncrypt(messageEt.getText().toString()));
					startActivity(sendIntent);
				}
				if (copyCheckBox.isChecked() && currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB)
				    copyText(messageEt.getText().toString());
				scrollView.scrollTo(0, scrollView.getBottom());
				
			}
			
		});
		
		
		btnDecrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String key = keyEt.getText().toString();
					while(key.length() < 16)
						key += 'x';
					encriptor.setAESKey(key);
					prefs.edit().putString(KEY_AES_KEY, keyEt.getText().toString()).apply();
					messageEt.setText(encriptor.AESDecrypt(messageEt.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		 

		
		showKeyToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					keyEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					keyEt.setEnabled(true);
				}
				else
				{
					keyEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					keyEt.setEnabled(false);
				}
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static void copyText(String text)
	{
		 ClipData clip = ClipData.newPlainText("Message", text);
		 clipboard.setPrimaryClip(clip);
	}
	
	
	
}
