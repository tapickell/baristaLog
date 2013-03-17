package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;

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
		lognotes.addAll(dataManager.getLogNotesByDevice(deviceName));
		logAdapter.notifyDataSetChanged();
	}

	// private class GetLogTask extends AsyncTask<Object, Object, Cursor> {
	//
	// DatabaseConnector databaseConnector = new
	// DatabaseConnector(LogList.this);
	//
	// @Override
	// protected Cursor doInBackground(Object... params) {
	// databaseConnector.open();
	// return databaseConnector.getAllLogs();
	// }
	//
	// @Override
	// protected void onPostExecute(Cursor result) {
	// logAdapter.changeCursor(result);
	// databaseConnector.close();
	// }
	//
	// }// end GetLogTask class

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// return super.onCreateOptionsMenu(menu);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.coffeelog_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent addNewCoffeeLog = new Intent(LogList.this,
				AddEditCoffeeLog.class);
		startActivity(addNewCoffeeLog);

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView arg0, View arg1, int arg2, long arg3) {
		Intent viewCoffeeLog = new Intent(LogList.this, ViewLog.class);
		viewCoffeeLog.putExtra(ROW_ID, arg3);
		startActivity(viewCoffeeLog);

	}

}// end CoffeeLog class
