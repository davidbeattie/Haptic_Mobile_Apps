package com.orange.receive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.message_effects;

/** This class is the same as InboxListItems **/

public class SentItems extends Activity {
	
	public ListView list_inbox;
	public String[] messages;
	Context context;
	Context deletedialog;
	VibeSystem m_vibration;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbox);
		
		context = this;
		deletedialog = this;
		m_vibration = new VibeSystem();
		m_vibration.onCreate(this);
		m_vibration.setEffects(message_effects.ivt);
		
		list_inbox = (ListView) findViewById(R.id.message_list);
		messages = getResources().getStringArray(R.array.message_in_array);
		HapticAdapter adapter = new HapticAdapter(messages, m_vibration);
		list_inbox.setAdapter(adapter);
		list_inbox.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
						m_vibration.playEffect(23);
						Intent intent = new Intent(context, NewMessage.class);
						intent.putExtra("inbox_list", position);
           	 			startActivity(intent);
           	 			finish();
						}
		});
		
		list_inbox.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
						m_vibration.playEffect(6);
						AlertDialog.Builder alertbox = new AlertDialog.Builder(deletedialog);
							alertbox.setTitle("MESSAGE");
							alertbox.setMessage("Delete Message?");
							alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									m_vibration.playEffect(20);
									Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_SHORT).show();
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
						return true;
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