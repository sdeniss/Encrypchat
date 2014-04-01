package com.projectunitato.encrypchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MessageSender {

	private Context mContext;
	
	
	public MessageSender(Context context)
	{
		mContext = context;
	}
	
	public void SendMessage(String meassage)
	{
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);         
		sendIntent.setData(Uri.parse("sms:"));
		sendIntent.putExtra("sms_body", meassage);
		mContext.startActivity(sendIntent);
	}
}
