package com.immersion.samples;

import android.content.Context;
import android.widget.LinearLayout ;
import android.widget.TextView;

/** Provides info for list items. Used by Haptic Adapter. **/

public class StringView extends LinearLayout {

	public StringView(Context context, String str) {
		super(context);
		
		setMinimumHeight(70);
		TextView t = new TextView(context);
		t.setTextSize(30);
		t.setPadding(10, 12, 0, 0);
		t.setText(str);
		
		
		addView(t);
	}

}
