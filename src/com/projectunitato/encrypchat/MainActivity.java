package com.projectunitato.encrypchat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.KeyPair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	
	/*
	 * Both keys will be saved.
	 * When starting a chat with a new contact, the PublicKey will be shared.
	 * Both keys are currently one-time generated.
	 */
	
	
	EditText et;
	Button btnEncrypt, btnDecrypt;
	String PRIVATE_KEY_KEY = "rsa_private";
	String PREF_NAME = "default";
	KeyPair kp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences prefs = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    String key = prefs.getString(PRIVATE_KEY_KEY, "");

    
    if (key.equals("")) {
        // generate KeyPair
        kp = Encriptor.genKey();
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

		
		et = (EditText) findViewById(R.id.et_message);
		btnEncrypt = (Button) findViewById(R.id.button_encrypt);
		btnDecrypt = (Button) findViewById(R.id.button_decrypt);
		
		btnEncrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:"));
				sendIntent.putExtra("sms_body", Encriptor.Encript(et.getText().toString(), kp));
				startActivity(sendIntent);
				
				*/
				et.setText(Encriptor.Encript(et.getText().toString(), kp));
			}
		});
		
		
		btnDecrypt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				et.setText(Encriptor.Decript(et.getText().toString(), kp));
			}
		});
	}
	
}
