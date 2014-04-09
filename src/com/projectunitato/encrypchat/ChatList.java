package com.projectunitato.encrypchat;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatList extends ArrayAdapter<String>{
	private final Activity context;
	private final ArrayList<String> web;
	private final ArrayList<Integer> imageId;
	
	public ChatList(Activity context, ArrayList<String> web, ArrayList<Integer> imageId, Integer image0, Integer image1) {
			super(context, R.layout.chat_single, web);
			this.context = context;
			this.web = web;
			this.imageId = imageId;
			}
	
	
	
	
	@Override
	public void add(String object) {
		this.web.add(object);
		this.imageId.add((Integer)imageId.toArray()[0]);
		notifyDataSetChanged();
	}
	
	public void postMessage(int sender, String message)
	{
		
	}




	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.chat_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText((String)web.toArray()[position]);
		imageView.setImageResource((Integer)imageId.toArray()[position]);
		return rowView;
	}
	
	
	
}
