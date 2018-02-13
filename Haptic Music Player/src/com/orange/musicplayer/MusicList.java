package com.orange.musicplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.immersion.samples.MusicAppHaptics;
import com.immersion.samples.VibeSystem;

/** This is the main class where all tracks are displayed in a list **/

public class MusicList extends Activity {
	
	VibeSystem m_vibration;
	Context context;
	Context context2;
	String[] artists;
	
	public ListView music_list;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(MusicAppHaptics.ivt);
        context = this;
        context2 = this;
        
        final ArrayList<MusicItems> artists = new ArrayList<MusicItems>();
        artists.add(new MusicItems("Learn 2 Fly","Foo Fighters", (R.drawable.foo_fighters_art), (R.raw.learn_to_fly), (5)));
        artists.add(new MusicItems("Moonlight Sonata","BeetHoven", (R.drawable.mozart_art), (R.raw.beethoven), (3)));
        artists.add(new MusicItems("Ghosts n Stuff","Deadmau5", (R.drawable.deadmaus_art), (R.raw.deadmaus), (4)));
        MusicListAdapter adapter = new MusicListAdapter(artists, m_vibration);        
        
        music_list = (ListView) findViewById(R.id.music_list);
       	music_list.setAdapter(adapter);     	
        music_list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent intent = new Intent(context, PlayStopControls.class);
					intent.putExtra("artists",artists.get(position));
					startActivity(intent);
					m_vibration.playEffect(11);
					}       	
        });
        
        music_list.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
					m_vibration.playEffect(0);
					Toast.makeText(getApplicationContext(), "Playing Haptic Preview", Toast.LENGTH_SHORT).show();
					if (position == 0){
						m_vibration.playEffect(6);
						}
					if (position == 1){
						m_vibration.playEffect(1);
						}
					if (position == 2){	
						m_vibration.playEffect(2);
						}
					return true;
					}	
        });
        
        final Button btn_back = (Button) findViewById(R.id.back);        
        btn_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				   	m_vibration.playEffect(12);			    		
				    Intent intent2 = new Intent(context2, HapticMusicPlayer.class);
				    startActivity(intent2);
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

