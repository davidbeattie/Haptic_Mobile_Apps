package com.immersion.samples;



import com.example.android.home.ApplicationInfo;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout ;
import android.widget.TextView;

public class ApplicationView extends LinearLayout {

	public ApplicationView(Context context, ApplicationInfo applicationInfo) {
		super(context);
		
		ImageView i = new ImageView(context);
		i.setImageDrawable(applicationInfo.icon);
		i.setAdjustViewBounds(true);
		i.setMaxHeight(50);
		i.setMaxWidth(50);
		addView(i);

		setMinimumHeight(70);
		TextView t = new TextView(context);
		t.setTextSize(18);
		t.setPadding(10, 12, 0, 0);
		t.setText(applicationInfo.title);	
		addView(t);
	}

	
}
