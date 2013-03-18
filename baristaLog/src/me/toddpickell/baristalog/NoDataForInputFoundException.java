package me.toddpickell.baristalog;

import android.util.Log;

public class NoDataForInputFoundException extends Exception {

	public NoDataForInputFoundException(String detailMessage) {
		super(detailMessage);
		Log.d("NoDataExcep", "NoDataForInputFound: this is not the column name you are looking for; " + detailMessage.toString());
	}


}
