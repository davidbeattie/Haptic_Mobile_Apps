package com.immersion.samples;

import java.util.ArrayList;

import com.example.android.home.ApplicationInfo;
import com.example.android.home.Home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HapticAdapter extends BaseAdapter {

	ArrayList<ApplicationInfo> listItems;
	VibeSystem m_vibration;
	
	public HapticAdapter(ArrayList<ApplicationInfo> mApplications, VibeSystem vibes){
		m_vibration = vibes;
		listItems = mApplications;
	}
	
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int position) {
		return listItems.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ApplicationView x = new ApplicationView(parent.getContext(),listItems.get(position));
		
		if (position == 0){
			m_vibration.playEffect(3);
		}
		else if (position == listItems.size()-1){
			m_vibration.playEffect(3);
		}
		else{
			m_vibration.playEffect(2);
		}
		return x;
	}
}
