package com.orange.musicplayer;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.immersion.samples.MusicAppHaptics;
import com.immersion.samples.VibeSystem;

/** This is the opening activity where a list is displayed showing a number
 	of ways to display music library**/

public class HapticMusicPlayer extends Activity {
    /** Called when the activity is first created. */
	
	VibeSystem m_vibration;
	public String[] library;
	Context context;
	
	public ListView music_library;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(MusicAppHaptics.ivt);
        context = this;
        library = getResources().getStringArray(R.array.library_array);
        music_library = (ListView) findViewById(R.id.library);
        
        HapticAdapter adapter = new HapticAdapter(library, m_vibration);
		music_library.setAdapter(adapter);

		music_library.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					m_vibration.playEffect(11);
					Intent intent = new Intent(context, MusicList.class);
					startActivity(intent);
					finish();	
					}			
			});
    }
	
	@Override
	protected void onDestroy()
    {
        m_vibration.onDestroy(this);        
        super.onDestroy();
    }
	
    @Override
	protected void onResume() 
    {        
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