package com.orange.receive;

import java.util.ArrayList;

/** Used to provide haptic effects on list scrolling **/

import com.immersion.samples.VibeSystem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HapticAdapter extends BaseAdapter {

	ArrayList<String> listItems;
	VibeSystem m_vibration;
	
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
			m_vibration.playEffect(22);
			}
		else if (position == listItems.size()-1){
			m_vibration.playEffect(22);
			}
		else{
			m_vibration.playEffect(21);
			}
		return x;
	}
}
