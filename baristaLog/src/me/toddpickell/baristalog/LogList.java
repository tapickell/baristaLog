package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class LogList extends ListActivity {

	private static final String ROW_ID = "row_id";
	private ListView coffeeLogListView;
	private String deviceName;

	// from manning example
	private DataManager dataManager;
	private LogAdapter logAdapter;
	private List<LogNote> lognotes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		coffeeLogListView = getListView();

		deviceName = getIntent().getStringExtra("device_name");

		dataManager = new DataManager(this);
		lognotes = new ArrayList<LogNote>();
		logAdapter = new LogAdapter(this, lognotes);

		coffeeLogListView.setAdapter(logAdapter);
		coffeeLogListView.setEmptyView(findViewById(R.id.main_list_empty));
		registerForContextMenu(coffeeLogListView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lognotes.clear();
		
		try {
			
			lognotes.addAll(dataManager.getLogNotesByDevice(deviceName));
			logAdapter.notifyDataSetChanged();
			for (LogNote note : lognotes) {
				Log.d("LOG_NOTE", note.toString());
			}

		} catch (NoDataForInputFoundException e) {
			Toast.makeText(this, "Sorry, the list is empty for this deice.", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// return super.onCreateOptionsMenu(menu);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.coffeelog_menu, menu);
		return true;
	}

	//???? not sure why this is here, research later
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent addNewCoffeeLog = new Intent(LogList.this,
				AddEditLog.class);
		startActivity(addNewCoffeeLog);

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView arg0, View arg1, int arg2, long arg3) {
//		Intent viewCoffeeLog = new Intent(LogList.this, ViewLog.class);
		Intent viewCoffeeLog = new Intent("me.toddpickell.baristalog.VIEWLOG");
		viewCoffeeLog.putExtra("device_name", deviceName);
		viewCoffeeLog.putExtra("log_note_index", arg2);
		startActivity(viewCoffeeLog);

	}

}// end CoffeeLog class
