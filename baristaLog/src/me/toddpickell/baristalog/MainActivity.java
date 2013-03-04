package me.toddpickell.baristalog;



import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private Spinner spinner;
	private TextView pre_text_view;
	private TextView bloom_text_view;
	private TextView brew_text_view;
	private TextView pre_text_timer;
	private TextView bloom_text_timer;
	private TextView brew_text_timer;
	private TextView sub_text_view;
	private TextView sub_text_timer;
	private TextView total_text_view;
	private TextView total_text_timer;
	private Button start_stop_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pre_text_view = (TextView) findViewById(R.id.pre_text_view);
		bloom_text_view = (TextView) findViewById(R.id.bloom_text_view);
		brew_text_view = (TextView) findViewById(R.id.brew_text_view);
		pre_text_timer = (TextView) findViewById(R.id.pre_text_timer);
		bloom_text_timer = (TextView) findViewById(R.id.bloom_text_timer);
		brew_text_timer = (TextView) findViewById(R.id.brew_text_timer);
		sub_text_view = (TextView) findViewById(R.id.sub_text_view);
		sub_text_timer = (TextView) findViewById(R.id.sub_text_timer);
		total_text_view = (TextView) findViewById(R.id.total_text_view);
		total_text_timer = (TextView) findViewById(R.id.total_text_timer);
		start_stop_button = (Button) findViewById(R.id.start_stop_button);
		
		//setup array adapter for spinner
		spinner = (Spinner) findViewById(R.id.devices_spinner);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.devices_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		//change timer state according to device selected
		switch (pos) {
		case 0:
			setStateForAeropress();
			break;

		case 1:
			setStateForChemex();
			break;
			
		case 2:
			setStateForClever();
			break;
			
		case 3:
			setStateForEspresso();
			break;
			
		case 4:
			setStateForFrenchPress();
			break;
			
		case 5:
			setStateForPourOver();
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}
	
	//will pull times from preferences file later
	private void setTimesForDeviceState(Integer pre, Integer bloom, Integer brew) {
		pre_text_timer.setText(DateUtils.formatElapsedTime(pre));
		bloom_text_timer.setText(DateUtils.formatElapsedTime(bloom));
		brew_text_timer.setText(DateUtils.formatElapsedTime(brew));
		sub_text_timer.setText(DateUtils.formatElapsedTime(pre));
		total_text_timer.setText(DateUtils.formatElapsedTime(pre + bloom + brew));
	}
	
	private void setupButtonForDevice() {
		start_stop_button.setText("START");
		start_stop_button.setBackgroundColor(getResources().getColor(R.color.GREEN));
	}
	
	private void setStateForAeropress() {
		Integer pre = 10;
		Integer bloom = 20;
		pre_text_view.setText(R.string.steep_text_view);
		bloom_text_view.setText(R.string.plunge_text_view);
		brew_text_view.setText("");
		sub_text_view.setText(R.string.steep_text_view);
		total_text_view.setText("Total");
		pre_text_timer.setText(DateUtils.formatElapsedTime(pre));
		bloom_text_timer.setText(DateUtils.formatElapsedTime(bloom));
		brew_text_timer.setText("");
		sub_text_timer.setText(DateUtils.formatElapsedTime(pre));
		total_text_timer.setText(DateUtils.formatElapsedTime((pre + bloom)));
		setupButtonForDevice();
	}
	
	private void setStateForChemex() {
		Integer pre = 30;
		Integer bloom = 30;
		Integer brew = 180;
		pre_text_view.setText(R.string.pre_text_view);
		bloom_text_view.setText(R.string.bloom_text_view);
		brew_text_view.setText(R.string.brew_text_view);
		sub_text_view.setText(R.string.pre_text_view);
		total_text_view.setText("Total");
		setTimesForDeviceState(pre, bloom, brew);
		setupButtonForDevice();
	}
	
	private void setStateForClever() {
		Integer pre = 30;
		Integer bloom = 30;
		Integer brew = 180;
		pre_text_view.setText(R.string.pre_text_view);
		bloom_text_view.setText(R.string.bloom_text_view);
		brew_text_view.setText(R.string.brew_text_view);
		sub_text_view.setText(R.string.pre_text_view);
		total_text_view.setText("Total");
		setTimesForDeviceState(pre, bloom, brew);
		setupButtonForDevice();
	}
	
	private void setStateForEspresso() {
		pre_text_view.setText("");
		bloom_text_view.setText("");
		brew_text_view.setText("");
		sub_text_view.setText("");
		total_text_view.setText("Total");
		pre_text_timer.setText("");
		bloom_text_timer.setText("");
		brew_text_timer.setText("");
		sub_text_timer.setText("");
		total_text_timer.setText(DateUtils.formatElapsedTime(0));
		setupButtonForDevice();
	}
	
	private void setStateForFrenchPress() {
		Integer pre = 30;
		Integer bloom = 180;
		Integer brew = 30;
		pre_text_view.setText(R.string.bloom_text_view);
		bloom_text_view.setText(R.string.brew_text_view);
		brew_text_view.setText(R.string.plunge_text_view);
		sub_text_view.setText(R.string.bloom_text_view);
		total_text_view.setText("Total");
		setTimesForDeviceState(pre, bloom, brew);
		setupButtonForDevice();
	}
	
	private void setStateForPourOver() {
		Integer pre = 30;
		Integer bloom = 30;
		Integer brew = 90;
		pre_text_view.setText(R.string.pre_text_view);
		bloom_text_view.setText(R.string.bloom_text_view);
		brew_text_view.setText(R.string.brew_text_view);
		sub_text_view.setText(R.string.pre_text_view);
		total_text_view.setText("Total");
		setTimesForDeviceState(pre, bloom, brew);
		setupButtonForDevice();
	}

}
