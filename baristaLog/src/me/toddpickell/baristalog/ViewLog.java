package me.toddpickell.baristalog;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_log);
		
		deviceName = getIntent().getStringExtra("device_name");
		logNoteIndex = getIntent().getIntExtra("log_note_index", 0);
		
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
		
		
	}
	
	
}
