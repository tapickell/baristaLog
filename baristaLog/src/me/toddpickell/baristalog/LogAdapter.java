package me.toddpickell.baristalog;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class LogAdapter extends ArrayAdapter<LogNote> {

	public LogAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public LogAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public LogAdapter(Context context, int textViewResourceId, LogNote[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public LogAdapter(Context context, int textViewResourceId,
			List<LogNote> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public LogAdapter(Context context, int resource, int textViewResourceId,
			LogNote[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public LogAdapter(Context context, int resource, int textViewResourceId,
			List<LogNote> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
