package com.orange.musicplayer;

import java.util.ArrayList;

import com.immersion.samples.VibeSystem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/** Adapter for list items in music library. Provides haptic effects on scroll**/

public class MusicListAdapter extends BaseAdapter {

	ArrayList<MusicItems> listItems;
	VibeSystem m_vibration;
	
	public MusicListAdapter(ArrayList<MusicItems> v, VibeSystem vibes){
		m_vibration = vibes;
		listItems = v;
	}
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {

		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MusicItems track = listItems.get(position);
		MusicView v = new MusicView(parent.getContext(), position, track.name, track.artist, track.artwork, track.music, track.haptics);
		return v; 
	}

}

