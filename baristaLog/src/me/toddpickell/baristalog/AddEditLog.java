package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditLog extends Activity {

	private String deviceName;
	private ArrayList<Integer> device_sub_times;
	private ArrayList<String> device_sub_titles;
	private String device_sub_title_one;
	private String device_sub_title_two;
	private String device_sub_title_three;
	private DeviceState device;
	private List<Integer> subTimes;
	private List<String> subTitles;

	@SuppressLint({ "NewApi", "DefaultLocale" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_log);

		deviceName = getIntent().getStringExtra("device_name");
		Log.d("DEBUG_ME!", "device name: " + deviceName) ;
		device = new DeviceState(this, deviceName);
		subTimes = device.getSubTimes();
		subTitles = device.getSubTitles();

		TextView device_label = (TextView) findViewById(R.id.device_label);
		TextView date_label = (TextView) findViewById(R.id.date_label);
		EditText coffee_notes = (EditText) findViewById(R.id.log_note_edit);
		EditText coffee_blend = (EditText) findViewById(R.id.blend_name_edit);
		TextView pre_label = (TextView) findViewById(R.id.pre_label);
		TextView bloom_label = (TextView) findViewById(R.id.bloom_label);
		TextView brew_label = (TextView) findViewById(R.id.brew_label);
		TextView pre_label_time = (TextView) findViewById(R.id.pre_label_time);
		TextView bloom_label_time = (TextView) findViewById(R.id.bloom_label_time);
		TextView brew_label_time = (TextView) findViewById(R.id.brew_label_time);
		

		coffee_notes.setMaxLines(2);
		// I am pretty sure this does nothing b/c it is an edittext w/ multiline and this is for textview
		Date date = new Date();
		java.text.DateFormat df = android.text.format.DateFormat.getDateFormat(this);
		device_label.setText(formatToCapWords(deviceName));
		
		pre_label.setText(subTitles.get(0));
		pre_label_time.setText(subTimes.get(0));// crashes like a little bitch!!!!!
		bloom_label.setText(subTitles.get(1));
		bloom_label_time.setText(subTimes.get(1));
		Log.d("DEBUG_ME!", "title one: " + device_sub_title_one + " title two: " + device_sub_title_two) ;
		if (device.getNumberLabels() > 2) {
			brew_label.setText(subTitles.get(2));
			brew_label_time.setText(subTimes.get(2));
			Log.d("DEBUG_ME!", "title three: " + device_sub_title_three) ;
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
