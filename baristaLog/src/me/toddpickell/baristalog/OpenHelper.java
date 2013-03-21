package me.toddpickell.baristalog;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper {
	
	private Context context;

	OpenHelper(final Context context) {
		super(context, "LOG_NOTES", null, DataManager.DATABASE_VERSION);
		this.context = context;
	}
	
	@Override
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		Log.d("PKL_TEST", "OpenHelper onOpen called");
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		LogTable.onCreate(db);
		Log.d("PKL_TEST", "OpenHelper onCreate called");
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
		LogTable.onUpgrade(db, oldVersion, newVersion);
		Log.d("PKL_TEST", "OpenHelper onUpgrade called");
	}

}
