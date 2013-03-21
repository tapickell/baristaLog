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

import com.EVSA.GUbu138802.Airpush;
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

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adView = (AdView) findViewById(R.id.ad);
		adView.setAdListener(this);
		AdRequest adRequest = new AdRequest();
		adRequest.addKeyword("coffee");
		adView.loadAd(adRequest);
		

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<Integer, Integer>();
		soundMap.put(TICK_SOUND_ID, soundPool.load(this, R.raw.tick, 1));
		soundMap.put(DING_SOUND_ID, soundPool.load(this, R.raw.ding, 1));
		soundMap.put(CLICK_SOUND_ID, soundPool.load(this, R.raw.click, 1));

		sub_text_view = (TextView) findViewById(R.id.sub_text_view);
		sub_text_timer = (TextView) findViewById(R.id.sub_text_timer);
		total_text_view = (TextView) findViewById(R.id.total_text_view);
		total_text_timer = (TextView) findViewById(R.id.total_text_timer);
		start_stop_button = (Button) findViewById(R.id.start_stop_button);
		add_log_button = (Button) findViewById(R.id.add_log_button);
		add_log_button.setClickable(false);
		add_log_button.setBackgroundColor(getResources().getColor(R.color.GRAY));

		subTimes = new ArrayList<Integer>();
		subTitles = new ArrayList<String>();
		deviceNames = new ArrayList<String>();

		deviceNames.add("aeropress");
		deviceNames.add("chemex");
		deviceNames.add("clever");
		deviceNames.add("espresso");
		deviceNames.add("french press");
		deviceNames.add("pour over");

		start_stop_button.setOnClickListener(this);
		add_log_button.setOnClickListener(this);

		// setup array adapter for spinner
		spinner = (Spinner) findViewById(R.id.devices_spinner);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.devices_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		total_text_view.setText("Total");
//		setStateForDevice(deviceNames.get(spinner.getSelectedItemPosition()));
		//trying this to set device from on create so it avoids null pointer
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		setStateForDevice(deviceNames.get(spinner.getSelectedItemPosition()));
	}

	@Override
    public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
        Log.d("ADD_ERROR", "error code: " + errorCode.toString());
    }

    @Override
    public void onReceiveAd(Ad ad) {
        Log.d("RCV_AD", "ad recvd: " + ad.toString());
    }

	@Override
	public void onClick(View view) {
		if (view.equals(start_stop_button)) {
			if (timerIsStopped()) {
				startTimer();
			} else {
				stopTimer();
			}
			soundPool.play(soundMap.get(CLICK_SOUND_ID), 1, 1, 1, 0, 1f);

		} else if (view.equals(add_log_button)) {
			launchAddEditLogView();			
		}
	}

	private void launchAddEditLogView() {
		Intent intent = new Intent("me.toddpickell.baristalog.ADDEDITLOGVIEW");

		intent.putExtra("device_name", device.getDevice_type());
		
		startActivity(intent);
	}

	private void stopTimer() {
		mHandler.removeMessages(0);
		mTotalTime = 0;
		setButtonToStart();
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
			if (add_log_button.isClickable()) {
				add_log_button.setClickable(false);
				add_log_button.setBackgroundColor(getResources().getColor(R.color.GRAY));
			}
			launchLogListView();
			break;
			
		default:
			break;
		}

		return false;
	}

	private void launchLogListView() {

		Intent intent = new Intent("me.toddpickell.baristalog.LOGLISTVIEW");
		intent.putExtra("device_name", device.getDevice_type());
		
		startActivity(intent);
		
	}

	private void launchDeviceSettingsMenu() {
		if (device.getDevice_type().equals(deviceNames.get(3))) {
			// do nothing for espresso
		} else {
			Intent intent = new Intent("me.toddpickell.baristalog.DEVICESETTINGSMENU");
			intent.putExtra("device_name", device.getDevice_type());
			startActivity(intent);
		}
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

	private void setButtonToStop() {
		start_stop_button.setText("STOP");
		start_stop_button.setBackgroundColor(getResources().getColor(
				R.color.RED));
		if (add_log_button.isClickable()) {
			add_log_button.setClickable(false);
			add_log_button.setBackgroundColor(getResources().getColor(R.color.GRAY));
		}
	}

	private void setButtonToStart() {
		start_stop_button.setText("START");
		start_stop_button.setBackgroundColor(getResources().getColor(
				R.color.GREEN));
		if (device.getDevice_type().equals(deviceNames.get(3))) {
			launchAddEditLogView();
		}
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
			// Log.d("DEBUGGERY", "Current: " + current/1000 + " TotalTime: " +
			// mTotalTime + " mStart: " + mStart/1000 + " Total: " +
			// ((mTotalTime/1000) + total));
			// Log.d("DEBUGGERY", "mSubTime: " + ((mTotalTime/1000) +
			// mSubTime));

			if (((mTotalTime / 1000) + mSubTime) <= 0) {
				// play tick.ogg
				soundPool.play(soundMap.get(TICK_SOUND_ID), 1, 1, 1, 0, 1f);

				// change sub title and time if there is one
				if (!subTitles.isEmpty()) {
					sub_text_view.setText(subTitles.get(0));
					subTitles.remove(0);
					if (!subTimes.isEmpty()) {
						// Log.d("DEBUGGERY", "mSubTime before change:" +
						// mSubTime);
						mSubTime = subTimes.get(0);
						subTimes.remove(0);
						// Log.d("DEBUGGERY", "mSubTime after change:" +
						// mSubTime);
						// Log.d("DEGUGGERY", "mTotalTime/1000: " +
						// (mTotalTime/1000));
						mSubTime -= (mTotalTime / 1000);
						// Log.d("DEBUGGERY", "mSubTime after change:" +
						// mSubTime);
						// Log.d("DEBUGGERY", "mSubTime inside is: " +
						// ((mTotalTime/1000) + mSubTime));
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

			if (((mTotalTime / 1000) + device.getTotal()) == 0
					&& device.getCountdown()) {
				stopTimer();
				// play ding.mp3
				soundPool.play(soundMap.get(DING_SOUND_ID), 1, 1, 1, 0, 1f);
				add_log_button.setClickable(true);
				add_log_button.setBackgroundColor(getResources().getColor(R.color.BLUE));

			} else {
				mHandler.sendEmptyMessageDelayed(0, 250);
			}
		};
	};

	@Override
	public void onDismissScreen(Ad arg0) {

	}

	@Override
	public void onLeaveApplication(Ad arg0) {

	}

	@Override
	public void onPresentScreen(Ad arg0) {
		
	}

}

