package ua.chntu.sheduler;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class MyService extends RemoteViewsService {

	final static String LOG_TAG = "myLogs";
    //Log.d(LOG_TAG, "");
	
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.d(LOG_TAG, "!!! MyService::onGetViewFactory");
		return new MyFactory(getApplicationContext(), intent);
	}

}