package com.orange.musicplayer;

import android.content.Context;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;

/** Sets content for list items for MusicListAdapter**/

public class MusicView extends AbsoluteLayout {

	public MusicView(Context context,int n, String name, String artist, int artwork, int music, int haptic) {
		super(context);
		
		TextView t = new TextView(context);
		t.setTextSize(14);
		t.setPadding(0, 0, 0, 0);
		t.setText("Track " + (n + 1));
		addView(t);
		
		TextView t2 = new TextView(context);
		t2.setTextSize(20);
		t2.setPadding(160, 30, 0, 0);
		t2.setText(artist);
		addView(t2);
		
		TextView t3 = new TextView(context);
		t3.setTextSize(18);
		t3.setPadding(160, 65, 0, 0);
		t3.setText(name);
		addView(t3);
			
		ImageView i = new ImageView(context);
		i.setImageResource(artwork);
		i.setAdjustViewBounds(true);
		i.setMaxHeight(140);
		i.setMaxWidth(140);
		i.setPadding(10, 30, 10, 10);
		addView(i);
	}
}