package com.orange.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.immersion.samples.MusicAppHaptics;
import com.immersion.samples.VibeSystem;


public class PlayStopControls extends Activity {
	
	VibeSystem m_vibration;
	Context context;
	Context context2;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playstop);
        m_vibration = new VibeSystem();
        m_vibration.onCreate(this);
        m_vibration.setEffects(MusicAppHaptics.ivt);
        context = this;
        context2 = this;
        
        final MusicItems track_info = (MusicItems) getIntent().getExtras().getSerializable("artists");
                
        final ImageView i = (ImageView) findViewById(R.id.image);
        i.setImageResource(track_info.artwork);
        i.setPadding(0, 0, 20, 40);
        i.setAdjustViewBounds(true);
        i.setMaxHeight(250);
        i.setMaxWidth(250);
        
        final TextView t = (TextView) findViewById(R.id.artist_playing);
        t.setText(track_info.artist);
        t.setTextSize(36);
        t.setPadding(10, 10, 0, 0);
        
        final MediaPlayer mp = MediaPlayer.create(context, track_info.music);
         
        final Button play_song = (Button) findViewById(R.id.play);
        play_song.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
            	m_vibration.playEffect(track_info.haptics);
            	mp.start();
        		}
        });
        
        final Button stop_song = (Button) findViewById(R.id.stop);
        stop_song.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
        		if(mp.isPlaying()){
        				m_vibration.stopEffects();
        		        mp.pause();
        		        mp.seekTo(0);
        				}	
        	}
        });
             
        final Button btn_back2 = (Button) findViewById(R.id.back2);
        btn_back2.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
        		m_vibration.playEffect(12);
        		mp.pause();
        		mp.seekTo(0);
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