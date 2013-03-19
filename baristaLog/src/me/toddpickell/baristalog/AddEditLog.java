package me.toddpickell.baristalog;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AddEditLog extends Activity {

	private String deviceName;

	@SuppressLint({ "NewApi", "DefaultLocale" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_log);

		deviceName = getIntent().getStringExtra("device_name");

		TextView device_label = (TextView) findViewById(R.id.device_label);
		TextView date_label = (TextView) findViewById(R.id.date_label);
		EditText coffee_notes = (EditText) findViewById(R.id.log_note_edit);
		EditText coffee_blend = (EditText) findViewById(R.id.blend_name_edit);


		coffee_notes.setMaxLines(2);
		// I am pretty sure this does nothing b/c it is an edittext w/ multiline and this is for textview
		
		Date date = new Date();
		device_label.setText(deviceName.substring(0, 1).toUpperCase()
				+ deviceName.substring(1));
		date_label.setText(date.toString());

		LinearLayout number_pickers_container = (LinearLayout) findViewById(R.id.number_pickers_container);
		TextView tamp_label = (TextView) findViewById(R.id.tamp_label);

		if (Build.VERSION.SDK_INT >= 11) {
			NumberPicker rating_picker = new NumberPicker(this);
			NumberPicker temp_picker = new NumberPicker(this);
			NumberPicker grind_picker = new NumberPicker(this);
			rating_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			temp_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			grind_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			// could vary these by device as well for optimal default settings.
			rating_picker.setMinValue(1);
			rating_picker.setMaxValue(5);
			rating_picker.setValue(5);
			rating_picker.setWrapSelectorWheel(false);
			rating_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			number_pickers_container.addView(rating_picker);

			temp_picker.setMinValue(150);
			temp_picker.setMaxValue(225);
			temp_picker.setValue(205);
			temp_picker.setWrapSelectorWheel(false);
			temp_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			number_pickers_container.addView(temp_picker);

			grind_picker.setMinValue(1);
			grind_picker.setMaxValue(40);
			grind_picker.setValue(10);
			grind_picker.setWrapSelectorWheel(false);
			grind_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			number_pickers_container.addView(grind_picker);

			if (deviceName.equals("espresso")) {

				NumberPicker tamp_picker = new NumberPicker(this);
				tamp_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				tamp_label.setText("Tamp");
				tamp_picker.setMinValue(20);
				tamp_picker.setMaxValue(40);
				tamp_picker.setValue(30);
				tamp_picker.setWrapSelectorWheel(false);
				tamp_picker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				number_pickers_container.addView(tamp_picker);
			}

		} else {

		}

	}

}
