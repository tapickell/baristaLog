package me.toddpickell.baristalog;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class AddEditLog extends Activity {

	private String deviceName;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_log);
		
		deviceName = getIntent().getStringExtra("device_name");
		
		LinearLayout rating_container = (LinearLayout) findViewById(R.id.rating_container);
		LinearLayout temp_container = (LinearLayout) findViewById(R.id.temp_container);
		LinearLayout grind_container = (LinearLayout) findViewById(R.id.grind_container);
		LinearLayout tamp_container = (LinearLayout) findViewById(R.id.tamp_container);
		
		if (Build.VERSION.SDK_INT >= 11) {
			NumberPicker rating_picker = new NumberPicker(this);
			NumberPicker temp_picker = new NumberPicker(this);
			NumberPicker grind_picker = new NumberPicker(this);
			
			//could vary these by device as well for optimal default settings.
			rating_picker.setMinValue(1);
			rating_picker.setMaxValue(5);
			rating_picker.setValue(5);
			rating_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			rating_container.addView(rating_picker);
			
			temp_picker.setMinValue(150);
			temp_picker.setMaxValue(225);
			temp_picker.setValue(205);
			temp_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			temp_container.addView(temp_picker);
			
			grind_picker.setMinValue(1);
			grind_picker.setMaxValue(40);
			grind_picker.setValue(10);
			grind_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			grind_container.addView(grind_picker);
			
			if (deviceName.equals("espresso")) {
				
				NumberPicker tamp_picker = new NumberPicker(this);
				
				tamp_picker.setMinValue(20);
				tamp_picker.setMaxValue(40);
				tamp_picker.setValue(30);
				tamp_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tamp_container.addView(tamp_picker);
			}
			
		} else {
			
		}
		
		
	}

}
