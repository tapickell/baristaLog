package me.toddpickell.baristalog;

import java.util.ArrayList;
import java.util.List;

import me.toddpickell.baristalog.LogTable.LogColumns;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

public class LogDao implements Dao<LogNote> {

	private static final String INSERT = "insert into " + LogTable.TABLE_NAME
			+ "(" 
			+ LogColumns.DEVICE + ", "
			+ LogColumns.NOTES + ", "
			+ LogColumns.PRE_TIME + ", "
			+ LogColumns.BLOOM_TIME + ", "
			+ LogColumns.BREW_TIME + ", "
			+ LogColumns.TEMP + ", "
			+ LogColumns.TAMP + ", "
			+ LogColumns.GRIND + ", "
			+ LogColumns.BLEND 
			+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	
	public LogDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	@Override
	public long save(LogNote entity) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, entity.getDevice());
		insertStatement.bindString(2, entity.getNotes());
		insertStatement.bindDouble(3, entity.getPre_time());
		insertStatement.bindDouble(4, entity.getBloom_time());
		insertStatement.bindDouble(5, entity.getBrew_time());
		insertStatement.bindDouble(6, entity.getTemp());
		insertStatement.bindDouble(7, entity.getTamp());
		insertStatement.bindDouble(8, entity.getGrind());
		insertStatement.bindString(9, entity.getBlend());
		
		return insertStatement.executeInsert();
	}

	@Override
	public void update(LogNote entity) {
		final ContentValues values = new ContentValues();
		values.put(LogColumns.DEVICE, entity.getDevice());
		values.put(LogColumns.NOTES, entity.getNotes());
		values.put(LogColumns.PRE_TIME, entity.getPre_time());
		values.put(LogColumns.BLOOM_TIME, entity.getPre_time());
		values.put(LogColumns.BREW_TIME, entity.getBrew_time());
		values.put(LogColumns.TEMP, entity.getTemp());
		values.put(LogColumns.TAMP, entity.getTamp());
		values.put(LogColumns.GRIND, entity.getGrind());
		values.put(LogColumns.BLEND, entity.getBlend());
		
		db.update(LogTable.TABLE_NAME, values, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
	}

	@Override
	public void delete(LogNote entity) {
		if (entity.getId() > 0) {
			db.delete(LogTable.TABLE_NAME, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
		}
		
	}

	@Override
	public LogNote get(long id) {
		LogNote lognote = null;
		Cursor c = db.query(LogTable.TABLE_NAME, 
						new String[] {
							BaseColumns._ID, 
							LogColumns.DEVICE,
							LogColumns.NOTES,
							LogColumns.PRE_TIME,
							LogColumns.BLOOM_TIME,
							LogColumns.BREW_TIME,
							LogColumns.TEMP,
							LogColumns.TAMP,
							LogColumns.GRIND,
							LogColumns.BLEND }, 
						BaseColumns._ID + " = ?", 
						new String[] { String.valueOf(id) }, 
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
		Cursor c = db.query(LogTable.TABLE_NAME, 
						new String[] {
							BaseColumns._ID, 
							LogColumns.DEVICE,
							LogColumns.NOTES,
							LogColumns.PRE_TIME,
							LogColumns.BLOOM_TIME,
							LogColumns.BREW_TIME,
							LogColumns.TEMP,
							LogColumns.TAMP,
							LogColumns.GRIND,
							LogColumns.BLEND }, 
						null, null, null, null, LogColumns.DEVICE, null);
		
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

	private LogNote buildLogNoteFromCursor(Cursor c) {
		LogNote lognote = null;
		if (c != null) {
			lognote = new LogNote();
			lognote.setId(c.getLong(0));
			lognote.setDevice(c.getString(1));
			lognote.setNotes(c.getString(2));
			lognote.setPre_time(c.getInt(3));
			lognote.setBloom_time(c.getInt(4));
			lognote.setBrew_time(c.getInt(5));
			lognote.setTemp(c.getInt(6));
			lognote.setTamp(c.getInt(7));
			lognote.setGrind(c.getInt(8));
			lognote.setBlend(c.getString(9));
		}
		
		return lognote;
	}
	
}



















