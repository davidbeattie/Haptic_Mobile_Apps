package com.orange.receive;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.message_effects;

/** This class provides 3 separate buttons representing a 
 	dummy messaging inbox, sent items and compose buttons **/

public class Inbox extends Activity {
	
	Context context;
	public static VibeSystem m_vibration;
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_menu);
		
		context = this;
		m_vibration = new VibeSystem();     
        m_vibration.onCreate(this);
        m_vibration.setEffects(message_effects.ivt);
				
		final Button inbox = (Button) findViewById(R.id.inbox);
		inbox.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(8);
				Intent intent = new Intent(context, InboxListItems.class);
           	 	startActivity(intent);
           	    } 		
		});
		
		final Button sent = (Button) findViewById(R.id.sent);
		sent.setOnClickListener (new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(7);
				Intent intent = new Intent(context, SentItems.class);
           	 	startActivity(intent);
           	    } 			
		});		
		
		final Button compose = (Button) findViewById(R.id.compose);
		compose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(9);
				Intent intent = new Intent(context, Compose.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
  	protected void onPause()
    {
		m_vibration.onPause(this);
        super.onPause();
     	}
    
	@Override
  	protected void onResume() 
  	{
		m_vibration.onResume(this);
        super.onResume();
  		}   
	
	@Override
  	protected void onDestroy()
    {
         m_vibration.onDestroy(this);
         super.onDestroy();
     	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK){
			m_vibration.playEffect(24);
			}
		return true;
	}
 }