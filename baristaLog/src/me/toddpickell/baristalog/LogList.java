package me.toddpickell.baristalog;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LogList extends ListActivity {

	private static final String ROW_ID = "row_id";
	private ListView coffeeLogListView;
	private CursorAdapter logAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		coffeeLogListView = getListView();
		coffeeLogListView.setOnItemClickListener(viewLogListener);
		
		//TODO map log field to textview in listview layout
		String[] from = new String[] { "name" };
//		int[] to = new int[] { R.id.coffeeLogTextView };
//		logAdapter = new SimpleCursorAdapter(LogList.this, R.layout.coffeeLog_list_item, null, from, to);
		
		setListAdapter(logAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	protected void onStop() {
		
		Cursor cursor = logAdapter.getCursor();
		if (cursor != null)
			cursor.deactivate();

		logAdapter.changeCursor(null);
		super.onStop();
	}
	
	private class GetLogTask extends AsyncTask<Object, Object, Cursor> {

		DatabaseConnector databaseConnector = new DatabaseConnector(LogList.this);
		
		@Override
		protected Cursor doInBackground(Object... params) {
			databaseConnector.open();	
			return databaseConnector.getAllLogs();
		}

		@Override
		protected void onPostExecute(Cursor result) {
			logAdapter.changeCursor(result);
			databaseConnector.close();
		}
		
	}// end GetLogTask class

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		return super.onCreateOptionsMenu(menu);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.coffeelog_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent addNewCoffeeLog = new Intent(LogList.this, AddEditCoffeeLog.class);
		startActivity(addNewCoffeeLog);
		
		return super.onOptionsItemSelected(item);
	}


	OnItemClickListener viewLogListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Intent viewCoffeeLog = new Intent(LogList.this, ViewLog.class);
			viewCoffeeLog.putExtra(ROW_ID, arg3);
			startActivity(viewCoffeeLog);

		}
	};
}//end CoffeeLog class
