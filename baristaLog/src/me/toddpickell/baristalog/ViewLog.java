package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewLog extends Activity {

	private DataManager dataManager;
	private ArrayList<LogNote> lognotes;
	private String deviceName;
	private Integer logNoteIndex;
	private LogNote logNote;
	private TextView device_label;
	private TextView date_label;
	private TextView coffee_notes;
	private TextView coffee_blend;
	private TextView pre_label;
	private TextView bloom_label;
	private TextView brew_label;
	private TextView tamp_label;
	private TextView rating_number_label;
	private TextView temp_number_label;
	private TextView grind_number_label;
	private TextView tamp_number_label;
	private DeviceState device;
	private List<String> subTitles;
	private TextView pre_label_time;
	private TextView bloom_label_time;
	private TextView brew_label_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_log);
		
		deviceName = getIntent().getStringExtra("device_name");
		logNoteIndex = getIntent().getIntExtra("log_note_index", 0);
		device = new DeviceState(this, deviceName);
		subTitles = device.getSubTitles();
		
		dataManager = new DataManager(this);
		lognotes = new ArrayList<LogNote>();
		Log.d("VIEW_LOG", "onCreate called");
		
		device_label = (TextView) findViewById(R.id.device_label);
		date_label = (TextView) findViewById(R.id.date_label);
		coffee_notes = (TextView) findViewById(R.id.log_note_label);
		coffee_blend = (TextView) findViewById(R.id.blend_name_label);
		pre_label = (TextView) findViewById(R.id.pre_label);
		bloom_label = (TextView) findViewById(R.id.bloom_label);
		brew_label = (TextView) findViewById(R.id.brew_label);
		tamp_label = (TextView) findViewById(R.id.tamp_label);
		rating_number_label = (TextView) findViewById(R.id.rating_number_label);
		temp_number_label = (TextView) findViewById(R.id.temp_number_label);
		grind_number_label = (TextView) findViewById(R.id.grind_number_label);
		tamp_number_label = (TextView) findViewById(R.id.tamp_number_label);
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		super.onResume();
		lognotes.clear();
		Log.d("VIEW_LOG", "onResume called");
		
		try {
			
			lognotes.addAll(dataManager.getLogNotesByDevice(deviceName));
			for (LogNote note : lognotes) {
				Log.d("LOG_NOTE", note.toString());
				logNote = lognotes.get(logNoteIndex);
				
			}

		} catch (NoDataForInputFoundException e) {
			Toast.makeText(this, "Sorry, the note is empty for this list.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		device_label.setText(formatToCapWords(deviceName));
		date_label.setText(logNote.getDate());
		coffee_notes.setText(logNote.getNotes());
		coffee_blend.setText(logNote.getBlend());

		LinearLayout time_container = (LinearLayout) findViewById(R.id.time_container);

		pre_label_time = new TextView(this);
		bloom_label_time = new TextView(this);
		brew_label_time = new TextView(this);

		pre_label_time.setTextSize(18.0F);
		bloom_label_time.setTextSize(18.0F);
		brew_label_time.setTextSize(18.0F);
		pre_label_time.setPadding(0, 10, 0, 0);
		bloom_label_time.setPadding(0, 10, 0, 0);
		brew_label_time.setPadding(0, 10, 0, 0);

		pre_label_time.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		bloom_label_time.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		brew_label_time.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		// this crashes resource not found exception vv
//		rating_number_label.setText(logNote.getRating());
//		temp_number_label.setText(logNote.getTemp());
//		grind_number_label.setText(logNote.getGrind());
		
		if (deviceName.equals("espresso")) {
			pre_label.setText("Time");
			tamp_label.setText("Tamp");
			tamp_number_label.setText(logNote.getTamp());
		} else {

			if (subTitles.size() > 0) {
				pre_label.setText(subTitles.get(0));
				pre_label_time.setText(logNote.getPre_time());// now crashes here???
			}
			if (subTitles.size() > 1) {
				bloom_label.setText(subTitles.get(1));
				bloom_label_time.setText(logNote.getBloom_time());
			}
			if (subTitles.size() > 2) {
				brew_label.setText(subTitles.get(2));
				brew_label_time.setText(logNote.getBrew_time());
			}
		}
		time_container.addView(pre_label_time);
		time_container.addView(bloom_label_time);
		time_container.addView(brew_label_time);
		
	}
	
	private String formatToCapWords(String words) {
		int space = words.indexOf(" ");
		String returnString;

		if (space > 0) {
			returnString = capWord(words.substring(0, space)) + " "
					+ capWord(words.substring(space + 1));

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
