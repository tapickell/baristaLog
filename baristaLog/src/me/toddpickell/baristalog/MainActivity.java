package me.toddpickell.baristalog;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private Spinner spinner;
	private TextView textView1;
	private TextView pre_text_view;
	private TextView bloom_text_view;
	private TextView brew_text_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		pre_text_view = (TextView) findViewById(R.id.pre_text_view);
		bloom_text_view = (TextView) findViewById(R.id.bloom_text_view);
		brew_text_view = (TextView) findViewById(R.id.brew_text_view);
		
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
		//not sure if starts from zero or one on position
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
	
	private void setTextFieldNormal() {
		pre_text_view.setText(R.string.pre_text_view);
		bloom_text_view.setText(R.string.bloom_text_view);
		brew_text_view.setText(R.string.brew_text_view);
	}
	
	private void setStateForAeropress() {
		textView1.setText("Aeropress");
		pre_text_view.setText(R.string.steep_text_view);
		bloom_text_view.setText(R.string.plunge_text_view);
		brew_text_view.setText("");		
	}
	
	private void setStateForChemex() {
		textView1.setText("Chemex");
		setTextFieldNormal();
	}
	
	private void setStateForClever() {
		textView1.setText("Clever");
		setTextFieldNormal();
	}
	
	private void setStateForEspresso() {
		textView1.setText("Espresso");
		pre_text_view.setText("");
		bloom_text_view.setText("");
		brew_text_view.setText("");
	}
	
	private void setStateForFrenchPress() {
		textView1.setText("French Press");
		pre_text_view.setText(R.string.bloom_text_view);
		bloom_text_view.setText(R.string.brew_text_view);
		brew_text_view.setText(R.string.plunge_text_view);
	}
	
	private void setStateForPourOver() {
		textView1.setText("Pour Over");
		setTextFieldNormal();
	}

}
