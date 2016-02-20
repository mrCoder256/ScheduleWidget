package ua.chntu.sheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerActivity extends Activity implements OnClickListener {

	final static String LOG_TAG = "myLogs";
	
	private int appWidgetId;
	
	private Button btnOk, btnCancel;
	private DatePicker datePicker;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_picker);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWidgetId = extras.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		Log.d(LOG_TAG, "appWidgetId=" + appWidgetId);
		
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		datePicker = (DatePicker) findViewById(R.id.datePicker);

		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnCancel: {			
				finish();
				break;
			}
			case R.id.btnOk: {	
				int day = datePicker.getDayOfMonth();
				int month = datePicker.getMonth();
				int year = datePicker.getYear();
				Calendar calendar = Calendar.getInstance();
			    calendar.set(year, month, day);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				String dateString = sdf.format(calendar.getTime());
				Log.d(LOG_TAG, dateString);
				
				Intent intent = new Intent(getApplicationContext(), MyProvider.class);
				intent.putExtra("day", dateString);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				intent.setAction(MyProvider.ACTION_CALENDAR);
				getApplicationContext().sendBroadcast(intent);
				
				finish();
				break;
			}
			default:
				break;
		}
	}

}
