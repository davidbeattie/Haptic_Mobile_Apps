package com.orange.receive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.message_effects;

/** This class is used for message composition **/

public class Compose extends Activity {
	
	Context senddialog;
	Context discarddialog;
	Context attachdialog;
	Context context;
	Context context2;
	EditText enter_txt;
	VibeSystem m_vibration;
	boolean focus_text;
	boolean focus_num;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.compose);
		enter_txt = (EditText) findViewById(R.id.enter_text);
		senddialog = this;
		discarddialog = this;
		attachdialog = this;
		context = this;
		context2 = this;
		m_vibration = new VibeSystem();
		m_vibration.onCreate(this);
		m_vibration.setEffects(message_effects.ivt);
		focus_num = false;
		focus_text = false;
		
		final Button send = (Button) findViewById(R.id.send_btn);
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (v == findViewById(R.id.send_btn)) {
					m_vibration.playEffect(1);
		           	AlertDialog.Builder alertbox = new AlertDialog.Builder(senddialog);
		               alertbox.setTitle("MESSAGE SENT");
		               alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface arg0, int arg1) {
		                   } 
		               });
		               alertbox.show();
		           	}
				}	
		});
		
		final Button attach = (Button) findViewById(R.id.attach_btn);
		attach.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(28);
				if (v == findViewById(R.id.attach_btn)) {
					final CharSequence[] items = {"Happy :)", "Angry :@", "Sad :(", "Love <3"};
		           	final AlertDialog.Builder alertbox = new AlertDialog.Builder(attachdialog);
		               alertbox.setTitle("ATTACH EMOTICON");		               
		               alertbox.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){		              
		                   public void onClick(DialogInterface dialog, int item) {
		                	   if (item == 0) {
		                		 m_vibration.playEffect(2); 
		                		 enter_txt.append(":)");
		                	   }
		                	   else if (item == 1){
		                		   m_vibration.playEffect(3);
		                		   enter_txt.append(":@");
		                	   }
		                	   else if (item == 2){
		                		   m_vibration.playEffect(5);
		                		   enter_txt.append(":(");
		                	   }
		                	   else if (item == 3){
		                		   m_vibration.playEffect(4);
		                		   enter_txt.append("<3");
		                	   }
		                	   dialog.cancel();
		                	   Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();		                       
		                	   return;
		                   }              
		               });   
		               alertbox.show();
		           }				
			}			
		});
		
		final Button discard = (Button) findViewById(R.id.discard_btn);
		discard.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(10);
				if (v == findViewById(R.id.discard_btn)) {
		           	AlertDialog.Builder alertbox = new AlertDialog.Builder(discarddialog);
		               alertbox.setTitle("DISCARD MESSAGE");
		               alertbox.setMessage("This will delete the message and its contents, are you sure?");
		               alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface arg0, int arg1) {
		                	   m_vibration.playEffect(20);
		                  	Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_SHORT).show();
		                   Intent intent = new Intent(context, Inbox.class);
		                   startActivity(intent);
		                   finish();
		                   } 
		               });
		               alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface arg0, int arg1) {
		                	   m_vibration.playEffect(19);
		                   }
		               });
		               alertbox.show();
		           	}
				}			
		});
		
		final EditText enter_num = (EditText) findViewById(R.id.enter_number);
		enter_num.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus && !(focus_num)) {
				enter_num.setText("");
				focus_num = true;
				}
			}
		});
		
		final EditText enter_txt = (EditText) findViewById(R.id.enter_text);
		enter_txt.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus && !(focus_text)) {
					enter_txt.setText("");
					focus_text = true;
					}
			}	
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK){
			m_vibration.playEffect(24);
			Intent intent2 = new Intent(context2, Inbox.class);
			startActivity(intent2);
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

	protected void onDestroy()
	{
		m_vibration.onDestroy(this);
	    super.onDestroy();
	    }
}
