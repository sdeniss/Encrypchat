package com.projectunitato.encrypchat;

import java.util.ArrayList;

import com.projectunitato.encrypchat.R.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

public class ChatActivity extends Activity{
	ListView list;
	EditText msgEt;
	ChatList adapter;
	Button sendBtn;
	
	  ArrayList<String> web;
	  ArrayList<Integer> imageId;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    web = new ArrayList<String>();
	    imageId = new ArrayList<Integer>();
	    web.add("STARTED");
	    imageId.add(R.drawable.image1);
	    setContentView(R.layout.activity_chat);
	    adapter = new ChatList(ChatActivity.this, web, imageId, R.drawable.image1, R.drawable.image2);
	    list = (ListView)findViewById(R.id.list);
	    msgEt = (EditText) findViewById(R.id.msg_et);
	    sendBtn = (Button) findViewById(R.id.button_sendMsg);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ChatActivity.this, "You Clicked at " +web.toArray()[+ position], Toast.LENGTH_SHORT).show();
                }

            });
        
        
        sendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int prevHeight = list.getHeight();
				adapter.add(msgEt.getText().toString());
				//list.scrollTo(0, Integer.parseInt(msgEt.getText().toString()));
				Log.i("TAG", "height " + list.getHeight() + " bottom " + list.getBottom());
				msgEt.setText("");
				
			}
		});
        
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Log.i("TAG", "SCROLL STATE " + list.getScrollX());		
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
						
			}
		});
        
	  }
}
