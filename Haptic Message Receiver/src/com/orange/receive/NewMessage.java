package com.orange.receive;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.message_effects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** This class creates a dummy received text message and also plays a 
 	delayed haptic effect **/

public class NewMessage extends Activity {
	
	Context deletedialog;
	Context context;
	Context context2;
	VibeSystem m_vibration;
	Runnable run;
	
	Runnable js = new Runnable() {	
		@Override
		public void run() {
			try {
				Thread.sleep(5000);	
			} 
			catch (InterruptedException e) {
			e.printStackTrace();
			}
			m_vibration.playEffect(4);
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message);
        
        deletedialog = this;
        context = this;
        context2 = this;
        m_vibration = new VibeSystem();
		m_vibration.onCreate(this);
		m_vibration.setEffects(message_effects.ivt);

		int inbox_list_items = this.getIntent().getExtras().getInt("inbox_list");
        String[] message_name = getResources().getStringArray(R.array.message_in_array);
        TextView message_from = (TextView) findViewById(R.id.message_from);
        message_from.setText(message_name[inbox_list_items]);
        message_from.setTextSize(24);
        message_from.setTextColor(Color.RED);
        
        Thread haptic = new Thread(js);
        haptic.start();
        
        final TextView message_text = (TextView)findViewById(R.id.message_content);        
        message_text.setText("Hi there, How are you? We're going to the cinema tonight. Do you want to come too? Text back :) x");
		message_text.setTextSize(19);
		
		final Button delete = (Button)findViewById(R.id.delete);
		delete.setOnClickListener (new OnClickListener(){

		@Override
		public void onClick(View v) {
			if (v == findViewById(R.id.delete)) {
				m_vibration.playEffect(10);
	           	AlertDialog.Builder alertbox = new AlertDialog.Builder(deletedialog);
	               alertbox.setTitle("MESSAGE");
	               alertbox.setMessage("Delete Message?");
	               alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface arg0, int arg1) {
	                	   m_vibration.playEffect(20);
	                	   Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_SHORT).show();
	                	   Intent intent2 = new Intent(context2, Inbox.class);
	                   		startActivity(intent2);
	                   		finish();
	                   		} 
	               });
	               alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface arg0, int arg1) {
	                	   m_vibration.playEffect(19);
	                       Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_SHORT).show();  
	                   		}
	               });
	               alertbox.show();
	           	}
			}
		});

		final Button reply = (Button)findViewById(R.id.reply);
		reply.setOnClickListener (new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(9);
				Intent intent = new Intent (context, Compose.class);
				startActivity(intent);
				finish();
				}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK){
			m_vibration.playEffect(24);
			}
		return true;
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
}