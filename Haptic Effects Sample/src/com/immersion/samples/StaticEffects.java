/*
** =========================================================================
** Copyright (c) 2010  Immersion Corporation.  All rights reserved.
**                     Immersion Corporation Confidential and Proprietary
**
** File:
**     StaticEffects.java
**
** Description: 
**     ImmVibe sample application for Android.
**
** =========================================================================
*/

package com.immersion.samples;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Simple example demonstrating playback of statically-designed effects
 * for handsets with and without support of the VibeTonz Player.<p>
 * 
 * This class also demonstrates usage of the VibeSystem
 * class created for these demonstrations and Android lifecycle functions.
 */
public class StaticEffects extends Activity implements OnItemClickListener {
    
    VibeSystem m_vibration;
    Context context;
    public String[] library;
    
    @Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.staticeffects);
        library = getResources().getStringArray(R.array.library_array);
        
        //
        // Initialize the vibration system with artist-designed vibration effects
        //
        // TS3000_ERM_ReferenceLibrary.ivt contains effects designed in TouchSense Studio
        // The second call to setEffects sets the Google Android Vibrator API effects
        // 
        // In this example, it does not matter that the content of the different
        // setEffect calls are radically different. In practice, design effects in
        // Immersion Studio then make an Android effect set with equivalent
        // effects at the same indices. That way, your code can call the same
        // effect indices no matter which underlying vibration system is present.
        //
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(CombinedHapticList.ivt);
        context = this;

        ListView listView = ((ListView)findViewById(R.id.effects));
        
        // populate the ListView with effect names
        listView.setAdapter(new HapticAdapter(library , m_vibration));
      
        // play an effect when a list item is clicked
        listView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (position == 0){
        	m_vibration.playEffect(15);
        }
        if (position == 1){
        	m_vibration.playEffect(10);
        }
        if (position == 2){
        	m_vibration.playEffect(11);
        }
        if (position == 3){
        	m_vibration.playEffect(12);      
        }
        if (position == 4){
        	m_vibration.playEffect(13);
        }
        if (position == 5){
        	m_vibration.playEffect(17);
        }
        if (position == 6){
        	m_vibration.playEffect(16);
        }
        if (position == 7){
        	m_vibration.playEffect(18);
        }
        if (position == 8){
        	m_vibration.playEffect(0);
        }
        if (position == 9){
        	m_vibration.playEffect(1);
        }
        if (position == 10){
        	m_vibration.playEffect(2);
        }
        if (position == 11){
        	m_vibration.playEffect(5);
        }
        if (position == 12){
        	m_vibration.playEffect(3);
        }
        if (position == 13){
        	m_vibration.playEffect(4);
        }
        if (position == 14){
        	m_vibration.playEffect(7);
        }
        if (position == 15){
        	m_vibration.playEffect(8);
        }
        if (position == 16){
        	m_vibration.playEffect(6);
        }
        if (position == 17){
        	m_vibration.playEffect(9);
        }
        if (position == 18){
        	m_vibration.playEffect(49);
        }
        if (position == 19){
        	m_vibration.playEffect(19);
        }
        if (position == 20){
        	m_vibration.playEffect(44);
        }
        if (position == 21){
        	m_vibration.playEffect(43);
        }
    	if (position == 22){
			m_vibration.playEffect(76);
		}
    	if (position == 23){
        	m_vibration.playEffect(21);
        }
    	if (position == 24){
        	m_vibration.playEffect(22);
        }
    	if (position == 25){
        	m_vibration.playEffect(25);
        }
    	if (position == 26){
        	m_vibration.playEffect(24);
        }
    	if (position == 27){
        	m_vibration.playEffect(26);
        }
    	if (position == 28){
        	m_vibration.playEffect(74);
        }
    	if (position == 29){
        	m_vibration.playEffect(43);
        }
    	if (position == 30){
        	m_vibration.playEffect(42);
        }
    	if (position == 31){
        	m_vibration.playEffect(20);
        }
    	if (position == 32){
        	m_vibration.playEffect(64);
        }
    	if (position == 33){
        	m_vibration.playEffect(63);
        }
    	if (position == 34){
        	m_vibration.playEffect(39);
        }
    	if (position == 35){
        	m_vibration.playEffect(38);
        }
    	if (position == 36){
        	m_vibration.playEffect(42);
        	Intent intent = new Intent(context, DragDropActivity.class);
        	startActivity(intent);
        }
    	
    }
}
