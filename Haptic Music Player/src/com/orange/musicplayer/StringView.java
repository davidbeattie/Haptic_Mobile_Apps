package com.orange.musicplayer;

import android.content.Context;
import android.widget.LinearLayout ;
import android.widget.TextView;

/** Sets content for list items using Haptic Adapter**/

public class StringView extends LinearLayout {

	public StringView(Context context, String str) {
		super(context);
		
		setMinimumHeight(70);
		TextView t = new TextView(context);
		t.setTextSize(24);
		t.setPadding(10, 12, 0, 0);
		t.setText(str);
		addView(t);
		}

}
