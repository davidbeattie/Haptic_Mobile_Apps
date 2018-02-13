package com.immersion.samples;

import android.app.Activity;
import android.os.Bundle;

import com.immersion.samples.VibeSystem;

public class DragDropActivity extends Activity {
	
	VibeSystem m_vibration;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(CombinedHapticList.ivt); 
        
        setContentView(new DrawView(this, m_vibration));
    }
    
    @Override
	protected void onDestroy()
    {
        m_vibration.onDestroy(this);
        
        super.onDestroy();
    }
    
    @Override
	protected void onResume() {
        
        m_vibration.onResume(this);
        
        super.onResume();
    }
    
    @Override
	protected void onPause()
    {
        m_vibration.onPause(this);
        
        super.onPause();
    }
}



