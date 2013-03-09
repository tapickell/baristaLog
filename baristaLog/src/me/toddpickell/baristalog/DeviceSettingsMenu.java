package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceSettingsMenu extends Activity implements
		OnSeekBarChangeListener, OnClickListener {

	private SeekBar preSlider;
	private SeekBar bloomSlider;
	private SeekBar brewSlider;
	private TextView deviceName;
	private TextView preSliderLabel, bloomSliderLabel, brewSliderLabel;
	private TextView preSliderTime, bloomSliderTime, brewSliderTime;
	private Button cancelButton;
	private Button resetButton;
	private Button saveButton;
	private List<Integer> subTimes;
	private List<String> subTitles;
	private DeviceState device;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_device);

		deviceName = (TextView) findViewById(R.id.device_name);
		preSlider = (SeekBar) findViewById(R.id.pre_seek_bar);
		bloomSlider = (SeekBar) findViewById(R.id.bloom_seek_bar);
		brewSlider = (SeekBar) findViewById(R.id.brew_seek_bar);
		preSliderLabel = (TextView) findViewById(R.id.pre_slider_label);
		bloomSliderLabel = (TextView) findViewById(R.id.bloom_slider_label);
		brewSliderLabel = (TextView) findViewById(R.id.brew_slider_label);
		preSliderTime = (TextView) findViewById(R.id.pre_slider_time);
		bloomSliderTime = (TextView) findViewById(R.id.bloom_slider_time);
		brewSliderTime = (TextView) findViewById(R.id.brew_slider_time);
		cancelButton = (Button) findViewById(R.id.cancel_settings_button);
		resetButton = (Button) findViewById(R.id.reset_settings_button);
		saveButton = (Button) findViewById(R.id.save_settings_button);

		device = new DeviceState(this, getIntent().getStringExtra("device_name"));
		subTimes = device.getSubTimes();
		subTitles = device.getSubTitles();

		preSlider.setOnSeekBarChangeListener(this);
		bloomSlider.setOnSeekBarChangeListener(this);
		brewSlider.setOnSeekBarChangeListener(this);

		cancelButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		String deviceNameLower = device.getDevice_type();
		String deviceNameFirstLetter = deviceNameLower.substring(0, 1)
				.toUpperCase();
		String deviceNameCapitalized = deviceNameFirstLetter
				.concat(deviceNameLower.substring(1));

		deviceName.setText(deviceNameCapitalized);

		preSlider.setProgress(subTimes.get(0));
		preSliderLabel.setText(subTitles.get(0));
		preSliderTime.setText(DateUtils.formatElapsedTime(subTimes.get(0)));
		bloomSlider.setProgress(subTimes.get(1));
		bloomSliderLabel.setText(subTitles.get(1));
		bloomSliderTime.setText(DateUtils.formatElapsedTime(subTimes.get(1)));
		if (subTimes.size() > 2) {
			brewSlider.setProgress(subTimes.get(2));
			brewSliderLabel.setText(subTitles.get(2));
			brewSliderTime
					.setText(DateUtils.formatElapsedTime(subTimes.get(2)));
		} else {
			brewSlider.setVisibility(8);
		}
	}
	
	private void persistSettings() {
		List<Integer> saveList = new ArrayList<Integer>();
		saveList.add(preSlider.getProgress());
		saveList.add(bloomSlider.getProgress());
		if (brewSlider.getVisibility() != 8) {
			saveList.add(brewSlider.getProgress());
		}	
//		Toast.makeText(this, "persisting: " + preSlider.getProgress() + " " + bloomSlider.getProgress() + " " + brewSlider.getProgress(), Toast.LENGTH_LONG).show();
		device.editDeviceTimes(saveList);
		Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.getId() == R.id.pre_seek_bar) {
			preSliderTime.setText(DateUtils.formatElapsedTime(preSlider
					.getProgress()));
		} else if (seekBar.getId() == R.id.bloom_seek_bar) {
			bloomSliderTime.setText(DateUtils.formatElapsedTime(bloomSlider
					.getProgress()));
		} else if (seekBar.getId() == R.id.brew_seek_bar) {
			brewSliderTime.setText(DateUtils.formatElapsedTime(brewSlider
					.getProgress()));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {

		case R.id.save_settings_button:

			persistSettings();
			finish();
			break;

		case R.id.reset_settings_button:
			
			device.resetDeviceToDefaults();
			finish();
			break;

		case R.id.cancel_settings_button:

			finish();
			break;
			
		default:
			break;
		}

	}

}
