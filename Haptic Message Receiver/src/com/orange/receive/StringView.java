package com.orange.receive;

import android.content.Context;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

/** This sets layout info for listviews **/

public class StringView extends AbsoluteLayout {

	public StringView(Context context, String str) {
		super(context);
		
		setMinimumHeight(70);
		TextView t = new TextView(context);
		TextView t2 = new TextView(context);
		t2.setTextSize(12);
		t.setTextSize(26);
		t2.setPadding(120, 20, 20, 20);
		t.setPadding(10, 12, 0, 0);
		t.setText(str);
		t2.setText("Hi there, How are you? We're going to the cinema tonight. Do you want to come too? Text back :) x");

		addView(t);
		addView(t2);
		
	}

}
