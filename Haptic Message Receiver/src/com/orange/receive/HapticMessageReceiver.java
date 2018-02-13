package com.orange.receive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.message_effects;

/** This is the first activity called when the app is started. This activity runs
 	as a separate dialog box used as an example of a received message **/

public class HapticMessageReceiver extends Activity {
    /** Called when the activity is first created. */
	VibeSystem m_vibration;
	Runnable run;
	Context readdialog;
	Context dismissdialog;
	Context context;
	Context context2;
	Context dialogone;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = this;
        context2 = this;
        readdialog = this;
        dismissdialog = this;
        dialogone = this;

        m_vibration = new VibeSystem();     
        m_vibration.onCreate(this);    
        m_vibration.setEffects(message_effects.ivt);
                
        AlertDialog.Builder alertbox = new AlertDialog.Builder(dialogone);
        alertbox.setTitle("NEW MESSAGE RECEIVED");
        alertbox.setMessage("Read Now?");
        
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
         	m_vibration.playEffect(34);
           	Intent intent = new Intent(context, NewMessage.class);
        	intent.putExtra("inbox_list", true);
       	 	startActivity(intent);
       	 	finish();
            } 
        });      
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            	m_vibration.playEffect(33);
            	Toast.makeText(getApplicationContext(), "Message saved in Inbox", Toast.LENGTH_SHORT).show();
            	finish();                
            }
        });   
        alertbox.show();
        h.sendEmptyMessage(0);        
    }
    
    Handler h = new Handler(){
    	public void handleMessage(Message msg){
    		switch (msg.what){
    		case 0:
    		m_vibration.playEffect(0);
    		break;
    		}
    	}
    };

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
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
    	super.onKeyDown(keyCode, event);
    	if (keyCode == KeyEvent.KEYCODE_BACK){
			m_vibration.playEffect(24);
			}
		return true;
	}
}
