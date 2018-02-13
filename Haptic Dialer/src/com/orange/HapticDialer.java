package com.orange;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.immersion.samples.VibeSystem;
import com.immersion.samples.ye;

/** This class is the main dialler app with 4 tabs featuring a number of lists and various buttons **/

public class HapticDialer extends Activity {
	
	public ListView list1;
	public ListView list2;
	public ListView list3;
	public EditText numberbox;
	boolean clearOnNextDigit;
	final int MAX_INPUT_LENGTH = 40;
	final int INPUT_MODE = 0;
	final int ERROR_MODE = 1;
	int displayMode;
	
	VibeSystem m_vibration;
	Context calldialog;
	Resources res;
	
	public String[] numbers;
	public String[] voicemail;
	public String[] contacts;
	public TabHost tabs;
	public static BroadcastReceiver test  = null;
	final Handler mHandler = new Handler();
	Runnable run;
	GestureDetector gestureScanner;  
    public  int currentYPosition; 
    public  int lastYPosition; 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        list1 = new ListView(HapticDialer.this);
        list2 = new ListView(HapticDialer.this);
        list3 = new ListView(HapticDialer.this);
        
        res = getResources();
        calldialog = this;
        
        setContentView(R.layout.main);
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(ye.ivt);
        
        tabs = (TabHost) this.findViewById(R.id.tabhost);
        tabs.setup();
        
        TabSpec tspec1 = tabs.newTabSpec("TabOne");
        tspec1.setIndicator("Dialer", res.getDrawable(R.drawable.dialtab));       
        tspec1.setContent(R.id.dialertab);
        tabs.addTab(tspec1);
        numberbox = (EditText)findViewById(R.id.numberbox);
		numberbox.setClickable(false);
		numberbox.setFocusable(false);
		numberbox.setSingleLine(true);
		numberbox.setCursorVisible(true);
               
        TabSpec tspec2 = tabs.newTabSpec("TabTwo");
        tspec2.setIndicator("Call Log", res.getDrawable(R.drawable.cltab));
        
        tspec2.setContent(new TabHost.TabContentFactory() {
			
		@Override
		public View createTabContent(String tag) {
			numbers = getResources().getStringArray(R.array.numbers_array);
			HapticAdapter adapter = new HapticAdapter(numbers, m_vibration);
			list2.setAdapter(adapter);
			return list2;	
			}
		}); 
        
        tabs.addTab(tspec2);
    
        TabSpec tspec3 = tabs.newTabSpec("TabThree");
        tspec3.setIndicator("Contacts", res.getDrawable(R.drawable.contab));	
        tspec3.setContent(new TabHost.TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				numbers = getResources().getStringArray(R.array.numbers_array);
				contacts = getResources().getStringArray(R.array.contacts_array);
				HapticAdapter adapter = new HapticAdapter(contacts, m_vibration);
				list1.setAdapter(adapter);
				return list1;
				}		
		});
        
        TabSpec tspec4 = tabs.newTabSpec("TabFour");
        tspec4.setIndicator("Voicemail",res.getDrawable(R.drawable.vmtab) );
        tspec4.setContent(new TabHost.TabContentFactory() {		
        	@Override
        	public View createTabContent(String tag) {
        		voicemail = getResources().getStringArray(R.array.voicemail_array);
        		HapticAdapter adapter = new HapticAdapter(voicemail, m_vibration);
        		list3.setAdapter(adapter);
        		return list3;	
				}		
        }); 
        
        tabs.addTab(tspec4);    
        tabs.addTab(tspec3);
    }
  
    void addDigitToDisplay(char abc){
		if (clearOnNextDigit)
			setDisplayString(" ");
			String inputString = getDisplayString();
		if ((!inputString.equals("0") || abc > 0)	&& inputString.length() < MAX_INPUT_LENGTH)
			{
				setDisplayString(inputString + abc);
			}	
		displayMode = INPUT_MODE;
		clearOnNextDigit = false;
	}
   
    void setDisplayString(String s){
		numberbox.setText(s);
		}
    String getDisplayString (){
		return 
		numberbox.getText().toString();
   		}
   
   	@Override
   	protected void onPause(){
		super.onPause();
		m_vibration.onPause(this);    
		}

   	@Override
   	protected void onDestroy()
   	{
	   	m_vibration.onDestroy(this);
	   	super.onDestroy();
   	   	}
  
   	@Override
   	protected void onResume(){
	   	super.onResume();
	   	m_vibration.onResume(this);
       
	   	final Button btn_call = (Button) findViewById(R.id.call);
	   	btn_call.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
           		m_vibration.playEffect(0);
           		if (v == findViewById(R.id.call)) {
           		AlertDialog.Builder alertbox = new AlertDialog.Builder(calldialog);
           		alertbox.setTitle("CALL CONNECTED");
           		alertbox.setMessage("Hang Up?");
           		alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           			public void onClick(DialogInterface arg0, int arg1) {
           				m_vibration.playEffect(27);
           				Toast.makeText(getApplicationContext(), "Call Ended", Toast.LENGTH_SHORT).show();
           				} 
           		});
           		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
           			public void onClick(DialogInterface arg0, int arg1) {
           				m_vibration.playEffect(6);
           				Toast.makeText(getApplicationContext(), "Unended Call Alert", Toast.LENGTH_SHORT).show();  
           				}
           		});
           		alertbox.show();
           		}
           }
       	});
 
       	final ImageButton btn_one = (ImageButton) findViewById(R.id.one);
       	btn_one.setOnTouchListener(new OnTouchListener(){
    	   	@Override
			public boolean onTouch(View v, MotionEvent event) {
    	   		if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	        	m_vibration.playEffect(21);
       	        	addDigitToDisplay('1');
       	        	btn_one.setImageDrawable(res.getDrawable(R.drawable.oneorange));
       	    		}
    	   		else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    		m_vibration.playEffect(22);
       	    		btn_one.setImageDrawable(res.getDrawable(R.drawable.onegrey));
       	    		}
    	   	return true;
    	   	}		
       	});   
   		btn_one.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				m_vibration.playEffect(10);
				return true;
				}      		
       	}); 		
   		btn_one.setOnClickListener (new OnClickListener(){
			@Override
			public void onClick(View v) {	
			} 		
   		});
       	
   		final ImageButton btn_two = (ImageButton) findViewById(R.id.two);
   		btn_two.setOnTouchListener(new OnTouchListener(){
   			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('2');
       	        btn_two.setImageDrawable(res.getDrawable(R.drawable.twoorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_two.setImageDrawable(res.getDrawable(R.drawable.twogrey));
       	    }
       	    return true;
   			}
   		});
       
   		final ImageButton btn_three = (ImageButton) findViewById(R.id.three);
   		btn_three.setOnTouchListener(new OnTouchListener(){
   			@Override
   			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('3');
       	        btn_three.setImageDrawable(res.getDrawable(R.drawable.threeorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_three.setImageDrawable(res.getDrawable(R.drawable.threegrey));
       	    }
       	    return true;
   			}
   		});
       
   		final ImageButton btn_four = (ImageButton) findViewById(R.id.four);
   		btn_four.setOnTouchListener(new OnTouchListener(){
   			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('4');
       	     btn_four.setImageDrawable(res.getDrawable(R.drawable.fourorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_four.setImageDrawable(res.getDrawable(R.drawable.fourgrey));
       	    }
       	    return true;
   			}
   		});
     
   		final ImageButton btn_five = (ImageButton) findViewById(R.id.five);
   		btn_five.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('5');
       	     btn_five.setImageDrawable(res.getDrawable(R.drawable.fiveorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_five.setImageDrawable(res.getDrawable(R.drawable.fivegrey));
       	    }
       	    return true;
			}
   		});

   		final ImageButton btn_six = (ImageButton) findViewById(R.id.six);
   		btn_six.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('6');
       	        btn_six.setImageDrawable(res.getDrawable(R.drawable.sixorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_six.setImageDrawable(res.getDrawable(R.drawable.sixgrey));
       	    }
       	    return true;
			}
   		});

   		final ImageButton btn_seven = (ImageButton) findViewById(R.id.seven);
   		btn_seven.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('1');
       	        btn_seven.setImageDrawable(res.getDrawable(R.drawable.sevenorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_seven.setImageDrawable(res.getDrawable(R.drawable.sevengrey));
       	    }
       	    return true;
       		}
   		});

   		final ImageButton btn_eight = (ImageButton) findViewById(R.id.eight);
   		btn_eight.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('8');
       	        btn_eight.setImageDrawable(res.getDrawable(R.drawable.eightorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_eight.setImageDrawable(res.getDrawable(R.drawable.eightgrey));
       	    }
       	    return true;
			}
   		});

   		final ImageButton btn_nine = (ImageButton) findViewById(R.id.nine);
       	btn_nine.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('9');
       	        btn_nine.setImageDrawable(res.getDrawable(R.drawable.nineorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_nine.setImageDrawable(res.getDrawable(R.drawable.ninegrey));
       	    }
       	    return true;
			}
       	});

       	final ImageButton btn_zero = (ImageButton) findViewById(R.id.zero);
       	btn_zero.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('0');
       	        btn_zero.setImageDrawable(res.getDrawable(R.drawable.zeroorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_zero.setImageDrawable(res.getDrawable(R.drawable.zerogrey));
       	    }
       	    return true;
			}
       	});

       	final ImageButton btn_star = (ImageButton) findViewById(R.id.star);
       	btn_star.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('*');
       	        btn_star.setImageDrawable(res.getDrawable(R.drawable.starover));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_star.setImageDrawable(res.getDrawable(R.drawable.star));
       	    }
       	    return true;
			}
       	});
       
       	final ImageButton btn_hash = (ImageButton) findViewById(R.id.hash);
       	btn_hash.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(21);
       	        addDigitToDisplay('#');
       	        btn_hash.setImageDrawable(res.getDrawable(R.drawable.hashorange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	m_vibration.playEffect(22);
       	    	btn_hash.setImageDrawable(res.getDrawable(R.drawable.hashgrey));
       	    }
       	    return true;
       		}
       	});
       
       	final ImageButton btn_add = (ImageButton) findViewById(R.id.add);        
       	btn_add.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
       	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
       	    	m_vibration.playEffect(9);
       	    	btn_add.setImageDrawable(res.getDrawable(R.drawable.add_contact_over_orange));
       	    }
       	    else if (event.getAction() == MotionEvent.ACTION_UP) {
       	    	tabs.setCurrentTab(3);
       	    	btn_add.setImageDrawable(res.getDrawable(R.drawable.add_contact_grey));
       	    }
       	    return true;
			}
       	});
       
       	final ImageButton btn_back = (ImageButton) findViewById(R.id.back);
       	btn_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				m_vibration.playEffect(38);
				setDisplayString(getDisplayString().substring(0,getDisplayString().length() -1));
	           	if (getDisplayString().length() < 1)
	           		 setDisplayString(" ");
				}
       	});
       	btn_back.setOnLongClickListener(new OnLongClickListener(){
       		@Override
       		public boolean onLongClick(View v) {
       			m_vibration.playEffect(41);
				numberbox.setText(" ");
			return true;
			}	   
       	});
       
       	list1.setOnItemClickListener(new OnItemClickListener(){
       		@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				tabs.setCurrentTab(0);
				m_vibration.playEffect(33);
				setDisplayString(numbers[position]);		
				}				
       	});
       	list1.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
						m_vibration.playEffect(10);
						AlertDialog.Builder alertbox = new AlertDialog.Builder(calldialog);
						alertbox.setTitle("DELETE CONTACT");
						alertbox.setMessage("Delete this Contact?");
						alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
									m_vibration.playEffect(27);
									Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
									} 
						});
						alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
									m_vibration.playEffect(25);
									}
						});
						alertbox.show();
				return true;
				}
       	});
       
       	list2.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
						tabs.setCurrentTab(0);
						m_vibration.playEffect(33);
						setDisplayString(numbers[position]);
			}		
       	});
       	list2.setOnItemLongClickListener(new OnItemLongClickListener(){
       		@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
       					m_vibration.playEffect(10);
       					AlertDialog.Builder alertbox = new AlertDialog.Builder(calldialog);
       					alertbox.setTitle("ADD CONTACT");
       					alertbox.setMessage("Add this number to contacts?");
       					alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
       						public void onClick(DialogInterface arg0, int arg1) {
       								m_vibration.playEffect(27);
       								Toast.makeText(getApplicationContext(), "Contact Added", Toast.LENGTH_SHORT).show();
       								} 
       					});
       					alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
       						public void onClick(DialogInterface arg0, int arg1) {
       								m_vibration.playEffect(25);
       								}
       					});
       					alertbox.show();
				return true;
       			}	
       	});
   
       	list3.setOnItemClickListener(new OnItemClickListener(){
       		@Override
       		public void onItemClick(AdapterView<?> parent, View view,
       				int position, long id) {
       					m_vibration.playEffect(33);
       					AlertDialog.Builder alertbox = new AlertDialog.Builder(calldialog);
       					alertbox.setTitle("VOICEMAIL");
       					alertbox.setMessage("Listen Now?");
       					alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
       						public void onClick(DialogInterface arg0, int arg1) {
       							m_vibration.playEffect(27);
       							Toast.makeText(getApplicationContext(), "Voicemail Retrieved", Toast.LENGTH_SHORT).show();
       							} 
       					});
       					alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
       						public void onClick(DialogInterface arg0, int arg1) {
       							m_vibration.playEffect(25);
       							}
       					});
       					alertbox.show();
   	           		}		
       	});
    
       	tabs.setOnFocusChangeListener(new OnFocusChangeListener(){       	
       		@Override
       		public void onFocusChange(View v, boolean hasFocus) {
       			m_vibration.playEffect(39);
   			   	}	
   			});
   		}   
}