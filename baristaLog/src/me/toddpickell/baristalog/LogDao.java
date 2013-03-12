package me.toddpickell.baristalog;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import me.toddpickell.baristalog.LogTable.LogColumns;

public class LogDao implements Dao<LogNote> {

	private static final String INSERT = "insert into " + LogTable.TABLE_NAME
			+ "(" LogColumns.DEVICE + ", "
			+ LogColumns.NOTES + ", "
			+ LogColumns.PRE_TIME + ", "
			+ LogColumns.BLOOM_TIME + ", "
			+ LogColumns.BREW_TIME + ", "
			+ LogColumns.TEMP + ", "
			+ LogColumns.TANP + ", "
			+ LogColumns.GRIND + ", "
			+ LogColumns.BLEND + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
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
	public void update(LogNote type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(LogNote type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LogNote get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogNote> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
