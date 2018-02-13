package com.orange.receiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.ye;

/** This class is a dummy activity where a phone call is being received. A number of
 	of buttons provided certain options are available i.e. Reject, Answer etc. **/

public class HapticCallReceiver extends Activity {
    /** Called when the activity is first created. */
	VibeSystem m_vibration;
	Runnable run;
	Context answerdialog;
	Context rejectdialog;
	Context divertdialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        answerdialog = this;
        rejectdialog = this;
        divertdialog = this;        
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(ye.ivt);
                
        final TextView txt_view = (TextView) findViewById(R.id.callingtext);
        txt_view.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		AlphaAnimation anim = new AlphaAnimation(1.0f, 0.1f);
    	        anim.setDuration(500);
    	        anim.setRepeatMode(Animation.REVERSE);
    	        anim.setRepeatCount(-1);
    	        txt_view.setAnimation(anim);
    	        txt_view.startAnimation(anim);   
        	run = new Runnable()
        	{
        	    public void run() 
        	    {
        	        m_vibration.playEffect(5);
        	        txt_view.postDelayed(this, 4000);
        	    	}
        		};
        		txt_view.postDelayed(run, 0);
        		}	
        });
        
        final Button btn_answer = (Button) findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	txt_view.removeCallbacks(run);
            	txt_view.clearAnimation();
            	m_vibration.stopEffects();
            	m_vibration.playEffect(0);
            	
            	 if (v == findViewById(R.id.btn_answer)) {
                     AlertDialog.Builder alertbox = new AlertDialog.Builder(answerdialog);
                     alertbox.setTitle("CALL CONNECTED");
                     alertbox.setMessage("Hang Up?");
                     alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface arg0, int arg1) {
                        	 Toast.makeText(getApplicationContext(), "Call Ended", Toast.LENGTH_SHORT).show();
                        	 m_vibration.playEffect(27);
                         	} 
                     });
                     alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface arg0, int arg1) {
                             Toast.makeText(getApplicationContext(), "Unended Call Alert", Toast.LENGTH_SHORT).show();
                             m_vibration.playEffect(6);
                         }
                     });
                     alertbox.show();
                 } 	
            }
        });
          
        final Button btn_divert = (Button) findViewById(R.id.btn_divert);
        btn_divert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	txt_view.removeCallbacks(run);
            	txt_view.clearAnimation();
            	m_vibration.stopEffects();
            	m_vibration.playEffect(2);
            	Toast.makeText(getApplicationContext(), "Call Diverted", Toast.LENGTH_SHORT).show();

            	if (v == findViewById(R.id.btn_divert)) {
                     AlertDialog.Builder alertbox = new AlertDialog.Builder(answerdialog);
                     alertbox.setTitle("1 VOICEMAIL RECIEVED");
                     alertbox.setMessage("Listen Now?");
                     m_vibration.playEffect(8);
                     alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface arg0, int arg1) {
                        	m_vibration.playEffect(27);
                        	Toast.makeText(getApplicationContext(), "Calling Voicemail", Toast.LENGTH_SHORT).show();
                         	} 
                     });
                     alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface arg0, int arg1) {
                            m_vibration.playEffect(25);
                         	}
                     });
                     alertbox.show();
                 }             	
            }
        });
            	
        final Button btn_reject = (Button) findViewById(R.id.btn_reject);
        btn_reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	txt_view.removeCallbacks(run);
            	txt_view.clearAnimation();
            	m_vibration.stopEffects();
            	m_vibration.playEffect(3);
            	
            	if (v == findViewById(R.id.btn_reject)) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(rejectdialog);
                    alertbox.setTitle("Confirm Action");
                    alertbox.setMessage("Are You Sure?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        	m_vibration.playEffect(27);
                        	Toast.makeText(getApplicationContext(), "Call Ended", Toast.LENGTH_SHORT).show();
                        	} 
                    });
                    alertbox.setNegativeButton("Silence?", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        	m_vibration.playEffect(7);
                            Toast.makeText(getApplicationContext(), "Call Silenced", Toast.LENGTH_SHORT).show();  
                        	}
                    });
                    alertbox.show();
            	}
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
}