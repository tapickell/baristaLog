package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	
	private Spinner spinner;
	
	private TextView sub_text_view;
	private TextView sub_text_timer;
	private TextView total_text_view;
	private TextView total_text_timer;
	private Button start_stop_button;
	
	private List<String> subTitles;
	private List<Integer> subTimes;
	private List<String> deviceNames;
	
	private Integer total;
	private long mStart = 0;
    private long mTotalTime = 0;
    private long mSubTime = 0;
    
    private Boolean countdown;
    
    private DeviceState device;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sub_text_view = (TextView) findViewById(R.id.sub_text_view);
		sub_text_timer = (TextView) findViewById(R.id.sub_text_timer);
		total_text_view = (TextView) findViewById(R.id.total_text_view);
		total_text_timer = (TextView) findViewById(R.id.total_text_timer);
		start_stop_button = (Button) findViewById(R.id.start_stop_button);
		
		subTimes = new ArrayList<Integer>();
		subTitles = new ArrayList<String>();
		deviceNames = new ArrayList<String>();
		
		deviceNames.add("areopress");
		deviceNames.add("chemex");
		deviceNames.add("clever");
		deviceNames.add("espresso");
		deviceNames.add("french press");
		deviceNames.add("pour over");
		
		start_stop_button.setOnClickListener(this);
		
		//setup array adapter for spinner
		spinner = (Spinner) findViewById(R.id.devices_spinner);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.devices_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		total_text_view.setText("Total");
	}

	@Override
	public void onClick(View view) {
		if (timerIsStopped()) {
			startTimer();
		} else {
			stopTimer();
		}
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
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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
		start_stop_button.setBackgroundColor(getResources().getColor(R.color.RED));
	}
	
	private void setButtonToStart() {
		start_stop_button.setText("START");
		start_stop_button.setBackgroundColor(getResources().getColor(R.color.GREEN));
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
			sub_text_timer.setText(DateUtils.formatElapsedTime(subTimes.get(0)));
			subTimes.remove(0);
		}
		if (subTitles.isEmpty()) {
			sub_text_view.setText("");
		} else {
			sub_text_view.setText(subTitles.get(0));
			subTitles.remove(0);
		}

		total_text_timer.setText(DateUtils.formatElapsedTime(device.getTotal()));
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
//            Log.d("DEBUGGERY", "Current: " + current/1000 + " TotalTime: " + mTotalTime + " mStart: " + mStart/1000 + " Total: " + ((mTotalTime/1000) + total));
            Log.d("DEBUGGERY", "mSubTime: " + ((mTotalTime/1000) + mSubTime));

            if (((mTotalTime/1000) + mSubTime) <= 0) {
				//change sub title and time if there is one
            	if (!subTitles.isEmpty()) {
					sub_text_view.setText(subTitles.get(0));
					subTitles.remove(0);
					if (!subTimes.isEmpty()) {
						Log.d("DEBUGGERY", "mSubTime before change:" + mSubTime);
						mSubTime = subTimes.get(0);
						subTimes.remove(0);
						Log.d("DEBUGGERY", "mSubTime after change:" + mSubTime);
						Log.d("DEGUGGERY", "mTotalTime/1000: " + (mTotalTime/1000));
						mSubTime -= (mTotalTime/1000);
						Log.d("DEBUGGERY", "mSubTime after change:" + mSubTime);
						Log.d("DEBUGGERY", "mSubTime inside is: " + ((mTotalTime/1000) + mSubTime));
					}
				}
			}
            if (device.getCountdown()) {
            	sub_text_timer.setText(DateUtils.formatElapsedTime((mTotalTime/1000) + mSubTime));
			}
            total_text_timer.setText(DateUtils.formatElapsedTime((mTotalTime/1000) + device.getTotal()));

            if (((mTotalTime/1000) + device.getTotal()) == 0 && device.getCountdown()) {
				stopTimer();
				
			} else {
				mHandler.sendEmptyMessageDelayed(0, 250);
			}
        };
    };

}
