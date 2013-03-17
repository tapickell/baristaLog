package me.toddpickell.baristalog;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LogAdapter extends ArrayAdapter<LogNote> {

	public LogAdapter(Context context, List<LogNote> lognotes) {
		super(context, R.layout.log_item, android.R.id.text1, lognotes);
		// not sure about android text1
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listItem = super.getView(position, convertView, parent);

		TextView text = null;
		ViewHolder holder = (ViewHolder) listItem.getTag();
		
		if (holder != null) {
			text = holder.text;
		} else {
			text = (TextView) listItem.findViewById(android.R.id.text1);
			holder = new ViewHolder(text);
			listItem.setTag(holder);
		}
		
		LogNote lognote = this.getItem(position);
		text.setText(lognote.getNotes());	// this should probably be more of a name b/c this is setting the android text1
		
		return listItem;		
		
	}

	private static class ViewHolder {
		protected final TextView text;

		public ViewHolder(TextView text) {
			this.text = text;
		}
	}

}
