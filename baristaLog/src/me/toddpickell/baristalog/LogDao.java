package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import me.toddpickell.baristalog.LogTable.LogColumns;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

public class LogDao implements Dao<LogNote> {

	private static final String INSERT = "insert into " + LogTable.TABLE_NAME
			+ "(" + LogColumns.DEVICE + ", " + LogColumns.NOTES + ", "
			+ LogColumns.DATE + ", " + LogColumns.RATING + ", "
			+ LogColumns.PRE_TIME + ", " + LogColumns.BLOOM_TIME + ", "
			+ LogColumns.BREW_TIME + ", " + LogColumns.TEMP + ", "
			+ LogColumns.TAMP + ", " + LogColumns.GRIND + ", "
			+ LogColumns.BLEND + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public LogDao(SQLiteDatabase db) {
		Log.d("SQL_SUX", db.toString());
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
		Log.d("SQL_SUX", insertStatement.toString());
	}

	@Override
	public long save(LogNote entity) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, entity.getDevice());
		insertStatement.bindString(2, entity.getNotes());
		insertStatement.bindString(3, entity.getDate());
		insertStatement.bindDouble(4, entity.getRating());
		insertStatement.bindDouble(5, entity.getPre_time());
		insertStatement.bindDouble(6, entity.getBloom_time());
		insertStatement.bindDouble(7, entity.getBrew_time());
		insertStatement.bindDouble(8, entity.getTemp());
		insertStatement.bindDouble(9, entity.getTamp());
		insertStatement.bindDouble(10, entity.getGrind());
		insertStatement.bindString(11, entity.getBlend());

		return insertStatement.executeInsert();
	}

	@Override
	public void update(LogNote entity) {
		final ContentValues values = new ContentValues();
		values.put(LogColumns.DEVICE, entity.getDevice());
		values.put(LogColumns.NOTES, entity.getNotes());
		values.put(LogColumns.DATE, entity.getDate());
		values.put(LogColumns.RATING, entity.getRating());
		values.put(LogColumns.PRE_TIME, entity.getPre_time());
		values.put(LogColumns.BLOOM_TIME, entity.getPre_time());
		values.put(LogColumns.BREW_TIME, entity.getBrew_time());
		values.put(LogColumns.TEMP, entity.getTemp());
		values.put(LogColumns.TAMP, entity.getTamp());
		values.put(LogColumns.GRIND, entity.getGrind());
		values.put(LogColumns.BLEND, entity.getBlend());

		db.update(LogTable.TABLE_NAME, values, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(entity.getId()) });
	}

	@Override
	public void delete(LogNote entity) {
		if (entity.getId() > 0) {
			db.delete(LogTable.TABLE_NAME, BaseColumns._ID + " = ?",
					new String[] { String.valueOf(entity.getId()) });
		}

	}

	@Override
	public LogNote get(long id) {
		LogNote lognote = null;
		Cursor c = db.query(LogTable.TABLE_NAME, new String[] {
				BaseColumns._ID, LogColumns.DEVICE, LogColumns.NOTES,
				LogColumns.DATE, LogColumns.RATING, LogColumns.PRE_TIME,
				LogColumns.BLOOM_TIME, LogColumns.BREW_TIME, LogColumns.TEMP,
				LogColumns.TAMP, LogColumns.GRIND, LogColumns.BLEND },
				BaseColumns._ID + " = ?", new String[] { String.valueOf(id) },
				null, null, null, "1");

		if (c.moveToFirst()) {
			lognote = this.buildLogNoteFromCursor(c);
		}

		if (!c.isClosed()) {
			c.close();
		}

		return lognote;
	}

	@Override
	public List<LogNote> getAll() {
		List<LogNote> list = new ArrayList<LogNote>();
		Cursor c = db.query(LogTable.TABLE_NAME, new String[] {
				BaseColumns._ID, LogColumns.DEVICE, LogColumns.NOTES,
				LogColumns.DATE, LogColumns.RATING, LogColumns.PRE_TIME,
				LogColumns.BLOOM_TIME, LogColumns.BREW_TIME, LogColumns.TEMP,
				LogColumns.TAMP, LogColumns.GRIND, LogColumns.BLEND }, null,
				null, null, null, LogColumns.DEVICE, null);

		if (c.moveToFirst()) {

			do {
				LogNote lognote = this.buildLogNoteFromCursor(c);
				if (lognote != null) {
					list.add(lognote);
				}
			} while (c.moveToNext());

		}

		if (!c.isClosed()) {
			c.close();
		}

		return list;
	}

	public List<LogNote> getAllLogsByDevice(String device) throws NoDataForInputFoundException {
		Log.d("SQL_SUX", "start of getAllLogsByDevice(" + device + ")");
		List<LogNote> list = new ArrayList<LogNote>();

		try {
			Cursor c = db.query(LogTable.TABLE_NAME, new String[] {
					BaseColumns._ID, LogColumns.DEVICE, LogColumns.NOTES,
					LogColumns.DATE, LogColumns.RATING, LogColumns.PRE_TIME,
					LogColumns.BLOOM_TIME, LogColumns.BREW_TIME,
					LogColumns.TEMP, LogColumns.TAMP, LogColumns.GRIND,
					LogColumns.BLEND },
			/* where */LogColumns.DEVICE + " = " + device,
			/* selection args */null,
			/* group by */null,
			/* having */null,
			/* order by */LogColumns.RATING);
			Log.d("SQL_SUX", c.toString());

			if (c.moveToFirst()) {

				do {
					LogNote lognote = this.buildLogNoteFromCursor(c);
					if (lognote != null) {
						list.add(lognote);
					}
				} while (c.moveToNext());

			}

			if (!c.isClosed()) {
				c.close();
			}
		} catch (SQLiteException e) {
			// TODO: handle exception
			Log.d("SQL_SUX", e.getMessage());
			throw new NoDataForInputFoundException(e.getMessage());
			// would like to throw an list is empty exception that
			// would be caught upwards and prevent empty list from being
			// displayed.
			// could also pop a toast that list is empty please add some items.
		}

		return list;
	}

	public List<LogNote> getAllLogsByBlend(String blend) throws NoDataForInputFoundException {
		List<LogNote> list = new ArrayList<LogNote>();
		try {
			Cursor c = db.query(LogTable.TABLE_NAME, new String[] {
					BaseColumns._ID, LogColumns.DEVICE, LogColumns.NOTES,
					LogColumns.DATE, LogColumns.RATING, LogColumns.PRE_TIME,
					LogColumns.BLOOM_TIME, LogColumns.BREW_TIME,
					LogColumns.TEMP, LogColumns.TAMP, LogColumns.GRIND,
					LogColumns.BLEND },
			/* where */LogColumns.BLEND + " = " + blend,
			/* selection args */null,
			/* group by */null,
			/* having */null,
			/* order by */LogColumns.RATING);

			if (c.moveToFirst()) {

				do {
					LogNote lognote = this.buildLogNoteFromCursor(c);
					if (lognote != null) {
						list.add(lognote);
					}
				} while (c.moveToNext());

			}

			if (!c.isClosed()) {
				c.close();
			}
		} catch (SQLiteException e) {
			// TODO: handle exception
			Log.d("SQL_SUX", e.getMessage());
			throw new NoDataForInputFoundException(e.getMessage());
			// would like to throw an list is empty exception that
			// would be caught upwards and prevent empty list from being
			// displayed.
			// could also pop a toast that list is empty please add some items.
		}

		return list;
	}

	private LogNote buildLogNoteFromCursor(Cursor c) {
		LogNote lognote = null;
		if (c != null) {
			lognote = new LogNote();
			lognote.setId(c.getLong(0));
			lognote.setDevice(c.getString(1));
			lognote.setNotes(c.getString(2));
			lognote.setDate(c.getString(3));
			lognote.setRating(c.getInt(4));
			lognote.setPre_time(c.getInt(5));
			lognote.setBloom_time(c.getInt(6));
			lognote.setBrew_time(c.getInt(7));
			lognote.setTemp(c.getInt(8));
			lognote.setTamp(c.getInt(9));
			lognote.setGrind(c.getInt(10));
			lognote.setBlend(c.getString(11));
		}

		return lognote;
	}

}
