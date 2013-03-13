package me.toddpickell.baristalog;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class LogTable {
	
	public static final String TABLE_NAME = "log";
	
	public static class LogColumns implements BaseColumns {
		public static final String DEVICE = "device";
		public static final String NOTES = "notes";
		public static final String DATE = "date";
		public static final String RATING = "rating";
		public static final String PRE_TIME = "pre_time";
		public static final String BLOOM_TIME = "bloom_time";
		public static final String BREW_TIME = "brew_time";
		public static final String TEMP = "temp";
		public static final String TAMP = "tamp";
		public static final String GRIND = "grind";
		public static final String BLEND = "blend";
		
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREAT TABLE " + LogTable.TABLE_NAME + " (");
		stringBuilder.append(LogColumns._ID + " INTEGER PRIMARY KEY, ");
		stringBuilder.append(LogColumns.DEVICE + " TEXT, ");
		stringBuilder.append(LogColumns.NOTES + " TEXT, ");
		stringBuilder.append(LogColumns.DATE + " TEXT, ");
		stringBuilder.append(LogColumns.RATING + " INTEGER, ");
		stringBuilder.append(LogColumns.PRE_TIME + " INTEGER, ");
		stringBuilder.append(LogColumns.BLOOM_TIME + " INTEGER, ");
		stringBuilder.append(LogColumns.BREW_TIME + " INTEGER, ");
		stringBuilder.append(LogColumns.TEMP + " INTEGER, ");
		stringBuilder.append(LogColumns.TAMP + " INTEGER, ");
		stringBuilder.append(LogColumns.GRIND + " INTEGER, ");
		stringBuilder.append(LogColumns.BLEND + " TEXT");
		stringBuilder.append(");");
		db.execSQL(stringBuilder.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLES IF EXISTS " + LogTable.TABLE_NAME);
		LogTable.onCreate(db);
	}

}
