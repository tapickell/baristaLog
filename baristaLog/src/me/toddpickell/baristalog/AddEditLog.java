package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditLog extends Activity {

	private String deviceName;
	private ArrayList<Integer> device_sub_times;
	private ArrayList<String> device_sub_titles;

	@SuppressLint({ "NewApi", "DefaultLocale" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_log);

		deviceName = getIntent().getStringExtra("device_name");
		device_sub_times = getIntent().getIntegerArrayListExtra("device_sub_times");
		device_sub_titles = getIntent().getStringArrayListExtra("device_sub_titles");

		TextView device_label = (TextView) findViewById(R.id.device_label);
		TextView date_label = (TextView) findViewById(R.id.date_label);
		EditText coffee_notes = (EditText) findViewById(R.id.log_note_edit);
		EditText coffee_blend = (EditText) findViewById(R.id.blend_name_edit);
		TextView pre_label = (TextView) findViewById(R.id.pre_label);
		TextView bloom_label = (TextView) findViewById(R.id.bloom_label);
		TextView brew_label = (TextView) findViewById(R.id.brew_label);

		coffee_notes.setMaxLines(2);
		// I am pretty sure this does nothing b/c it is an edittext w/ multiline and this is for textview
		Date date = new Date();
		java.text.DateFormat df = android.text.format.DateFormat.getDateFormat(this);

		device_label.setText(formatToCapWords(deviceName));
		
		if (!device_sub_titles.isEmpty()) {
			//threw a null pointer exception??
			try {
				pre_label.setText(device_sub_titles.get(0));
				bloom_label.setText(device_sub_titles.get(1));
				if (device_sub_titles.size() > 2) {
					brew_label.setText(device_sub_titles.get(3));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(this,
						"Sorry, you threw a null pointer dumb-ass.",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this,
					"Sorry, you ArrayList is empty dumb-ass.",
					Toast.LENGTH_LONG).show();
		}
		date_label.setText(df.format(date));

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

	private String formatToCapWords(String words) {
		int space = words.indexOf(" ");
		String returnString;
		
		if (space > 0) {
			returnString = capWord(words.substring(0, space)) + " " + capWord(words.substring(space + 1));
			
		} else {
			returnString = capWord(words);
		}
		
		return returnString;
	}

	@SuppressLint("DefaultLocale")
	private String capWord(String word) {
		
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

}
