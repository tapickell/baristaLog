package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
//Ad network-specific imports (AdMob).

public class MainActivity extends Activity implements OnItemSelectedListener, OnClickListener, AdListener {

	private static final Integer TICK_SOUND_ID = 0;
	private static final Integer DING_SOUND_ID = 1;
	private static final Integer CLICK_SOUND_ID = 2;

	private Spinner spinner;

	private TextView sub_text_view;
	private TextView sub_text_timer;
	private TextView total_text_view;
	private TextView total_text_timer;
	private Button start_stop_button;
	private Button add_log_button;

	private List<String> subTitles;
	private List<Integer> subTimes;
	private List<String> deviceNames;

	private long mStart = 0;
	private long mTotalTime = 0;
	private long mSubTime = 0;

	private DeviceState device;

	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap;
	private AdView adView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeAds();
		initializeSound();

		getViews();
		initializeSaveLogButton();

		subTimes = new ArrayList<Integer>();
		subTitles = new ArrayList<String>();
		initializeDeviceNamesList();

		start_stop_button.setOnClickListener(this);
		add_log_button.setOnClickListener(this);

		initializeDeviceNameSpinner();

		total_text_view.setText("Total");

	}

	/****  Initialize Methods used in onCreate ****/
	private void initializeDeviceNameSpinner() {
		spinner = (Spinner) findViewById(R.id.devices_spinner);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.devices_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void initializeDeviceNamesList() {
		deviceNames = new ArrayList<String>();

		deviceNames.add("aeropress");
		deviceNames.add("chemex");
		deviceNames.add("clever");
		deviceNames.add("espresso");
		deviceNames.add("french press");
		deviceNames.add("pour over");
	}

	private void getViews() {
		sub_text_view = (TextView) findViewById(R.id.sub_text_view);
		sub_text_timer = (TextView) findViewById(R.id.sub_text_timer);
		total_text_view = (TextView) findViewById(R.id.total_text_view);
		total_text_timer = (TextView) findViewById(R.id.total_text_timer);
		start_stop_button = (Button) findViewById(R.id.start_stop_button);
	}

	private void initializeSaveLogButton() {
		add_log_button = (Button) findViewById(R.id.add_log_button);
		add_log_button.setClickable(false);
		add_log_button.setBackgroundColor(getResources().getColor(R.color.GRAY));
	}

	@SuppressLint("UseSparseArrays")
	private void initializeSound() {
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<Integer, Integer>();
		soundMap.put(TICK_SOUND_ID, soundPool.load(this, R.raw.tick, 1));
		soundMap.put(DING_SOUND_ID, soundPool.load(this, R.raw.ding, 1));
		soundMap.put(CLICK_SOUND_ID, soundPool.load(this, R.raw.click, 1));
	}

	private void initializeAds() {
		adView = (AdView) findViewById(R.id.ad);
		adView.setAdListener(this);
		AdRequest adRequest = new AdRequest();
		adRequest.addKeyword("coffee");
		adView.loadAd(adRequest);
	}

	/**** Google ad method overrides ****/
	@Override
    public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
        Log.d("ADD_ERROR", "error code: " + errorCode.toString());
    }

    @Override
    public void onReceiveAd(Ad ad) {
        Log.d("RCV_AD", "ad recvd: " + ad.toString());
    }

    /**** Button interaction methods ****/
	@Override
	public void onClick(View view) {
		if (view.equals(start_stop_button)) {
			startStopButtonPress();

		} else if (view.equals(add_log_button)) {
			launchAddEditLogView();			
		}
	}

	private void startStopButtonPress() {
		if (timerIsStopped()) {
			startTimer();
		} else {
			stopTimer();
		}
		soundPool.play(soundMap.get(CLICK_SOUND_ID), 1, 1, 1, 0, 1f);
	}

	private void enableSaveLogButton() {
		if (!add_log_button.isClickable()) {
			add_log_button.setClickable(true);
			add_log_button.setBackgroundColor(getResources().getColor(R.color.BLUE));
		}
	}
	
	private void disableSaveLogButton() {
		if (add_log_button.isClickable()) {
			add_log_button.setClickable(false);
			add_log_button.setBackgroundColor(getResources().getColor(R.color.GRAY));
		}
	}
	
	private void setButtonToStop() {
		start_stop_button.setText("STOP");
		start_stop_button.setBackgroundColor(getResources().getColor(
				R.color.RED));
		disableSaveLogButton();
	}

	private void setButtonToStart() {
		start_stop_button.setText("START");
		start_stop_button.setBackgroundColor(getResources().getColor(
				R.color.GREEN));
	}
	
	/**** activity launching methods ****/
	private void launchAddEditLogView() {
		Intent intent = new Intent("me.toddpickell.baristalog.ADDEDITLOGVIEW");
		intent.putExtra("device_name", device.getDevice_type());
		if (device.getDevice_type().equals("espresso")) {
			intent.putExtra("shot_time", total_text_timer.getText());
		}
		startActivity(intent);
	}

	private void launchLogListView() {
		startIntentByName("LOGLISTVIEW");
	}

	private void launchDeviceSettingsMenu() {
		if (device.getDevice_type().equals(deviceNames.get(3))) {
			// do nothing for espresso
		} else {
			startIntentByName("DEVICESETTINGSMENU");
		}
	}
	
	private void startIntentByName(String activityName) {
		Intent intent = new Intent("me.toddpickell.baristalog." + activityName);
		intent.putExtra("device_name", device.getDevice_type());
		startActivity(intent);
	}
	
	/**** timer interaction methods ****/
	private void stopTimer() {
		mHandler.removeMessages(0);
		mTotalTime = 0;
		setButtonToStart();
		if (device.getDevice_type().equals(deviceNames.get(3))) {
			enableSaveLogButton();
		}
	}

	private void startTimer() {
		mStart = System.currentTimeMillis();
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessage(0);
		setButtonToStop();
	}

	private boolean timerIsStopped() {
		return !mHandler.hasMessages(0);
	}

	/****  options menu interactions ****/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.edit_settings:
			launchDeviceSettingsMenu();
			break;

		case R.id.log_list_view:
			disableSaveLogButton();
			launchLogListView();
			break;
			
		default:
			break;
		}

		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if (!timerIsStopped()) {
			stopTimer();
		}
		setStateForDevice(deviceNames.get(pos));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}



	public Context getContext() {
		return this;
	}

	private void setStateForDevice(String device_name) {
		device = new DeviceState(this, device_name);
		subTimes = device.getSubTimes();
		subTitles = device.getSubTitles();
		if (subTimes.isEmpty()) {
			mSubTime = 0;
			sub_text_timer.setText("");
		} else {
			mSubTime = subTimes.get(0);
			sub_text_timer
					.setText(DateUtils.formatElapsedTime(subTimes.get(0)));
			subTimes.remove(0);
		}
		if (subTitles.isEmpty()) {
			sub_text_view.setText("");
		} else {
			sub_text_view.setText(subTitles.get(0));
			subTitles.remove(0);
		}

		total_text_timer
				.setText(DateUtils.formatElapsedTime(device.getTotal()));
		setButtonToStart();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			long current = System.currentTimeMillis();
			if (device.getCountdown()) {
				mTotalTime -= current - mStart;
			} else {
				mTotalTime += current - mStart;
			}
			mStart = current;

			if (((mTotalTime / 1000) + mSubTime) <= 0) {
				// play tick.ogg
				soundPool.play(soundMap.get(TICK_SOUND_ID), 1, 1, 1, 0, 1f);

				if (!subTitles.isEmpty()) {
					sub_text_view.setText(subTitles.get(0));
					subTitles.remove(0);
					if (!subTimes.isEmpty()) {

						mSubTime = subTimes.get(0);
						subTimes.remove(0);
						mSubTime -= (mTotalTime / 1000);
					}
				}
			}
			
			if (device.getCountdown()) {
				sub_text_timer.setText(DateUtils
						.formatElapsedTime((mTotalTime / 1000) + mSubTime));
			}
			
			total_text_timer
					.setText(DateUtils.formatElapsedTime((mTotalTime / 1000)
							+ device.getTotal()));

			if (((mTotalTime / 1000) + device.getTotal()) == 0 && device.getCountdown()) {
				stopTimer();
				// play ding.mp3
				soundPool.play(soundMap.get(DING_SOUND_ID), 1, 1, 1, 0, 1f);
				
				enableSaveLogButton();

			} else {
				mHandler.sendEmptyMessageDelayed(0, 250);
			}
		};
	};

	/**** Activity state method overrides ****/
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("PKL_TEST", "onResume called");
		setStateForDevice(deviceNames.get(spinner.getSelectedItemPosition()));
		// TODO find a better way to do this 
	}
	
	@Override
	public void onDismissScreen(Ad arg0) {
		Log.d("PKL_TEST", "onDismissScreen called");
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		Log.d("PKL_TEST", "onLeaveApplication called");
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		Log.d("PKL_TEST", "onPresentScreen called");
	}

}

