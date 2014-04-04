package com.projectunitato.encrypchat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	
	ToggleButton showKeyToggle;
	EditText messageEt, keyEt;
	Button btnEncrypt, btnDecrypt;
	String PRIVATE_KEY_KEY = "rsa_private";
	String KEY_AES_KEY = "aes_key";
	String PREF_NAME = "default";
	Encriptor encriptor;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
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
		
		keyEt.setText(aespref);
		
		
		btnEncrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:"));
				sendIntent.putExtra("sms_body", Encriptor.Encript(et.getText().toString(), kp));
				startActivity(sendIntent);
				
				*/
				while(keyEt.getText().toString().length() < 16)
					keyEt.setText(keyEt.getText().toString() + 'x');
				encriptor.setAESKey(keyEt.getText().toString());
				prefs.edit().putString(KEY_AES_KEY, keyEt.getText().toString()).apply();
				messageEt.setText(encriptor.AESEncrypt(messageEt.getText().toString()));
			}
			
		});
		
		
		btnDecrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					while(keyEt.getText().toString().length() < 16)
						keyEt.setText(keyEt.getText().toString() + 'x');
					encriptor.setAESKey(keyEt.getText().toString());
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
	
}
