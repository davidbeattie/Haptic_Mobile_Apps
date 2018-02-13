package com.immersion.samples;

import java.util.ArrayList;

/** This class allows haptic effects to be applied on listitems 
	when they are created within a listview **/

import com.immersion.samples.VibeSystem;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/** Allows haptic effects to be played when list items are created during scrolling **/

public class HapticAdapter extends BaseAdapter {

	ArrayList<String> listItems;
	VibeSystem m_vibration;
	Context context;
	
	public HapticAdapter(String[] x, VibeSystem vibes){
		m_vibration = vibes;
		listItems = new ArrayList<String>();
		for (int i = 0; i < x.length; i++){
			listItems.add(x[i]);
		}
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
		StringView x = new StringView(parent.getContext(),listItems.get(position));
		
		if (position == 0){
			m_vibration.playEffect(41);
		}
		else if (position == listItems.size()-1){
			m_vibration.playEffect(41);
		}
		else{
			m_vibration.playEffect(40);
		}
		
		return x;
	}

}
