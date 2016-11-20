package ua.chntu.sheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.chntu.sheduler.DateWrapper.Day;
import ua.chntu.sheduler.excel.ReadExcelTest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class MyProvider extends AppWidgetProvider {

	private final static String LOG_TAG = "myLogs";

	private static final String ACTION_MENU = "MENU";
	private static final String ACTION_NEXT = "NEXT";
	private static final String ACTION_PREVIOUS = "PREV";
	public static final String ACTION_CALENDAR = "CALENDAR";
	private static final String ACTION_TODAY = "TODAY";
	private static boolean show_buttons = false;
	private static ScheduleProvider scheduler;
	
	static {
		scheduler = new ScheduleProvider();
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for (int i : appWidgetIds) {
			updateWidget(context, appWidgetManager, i);
		}
	}

	void updateWidget(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget);

		Day day = scheduler.getToday();
		Log.d(LOG_TAG, "start day: " + scheduler.getDate(day));
		remoteViews.setTextViewText(R.id.tvTitle, scheduler.getDate(day));

		setupEvents(remoteViews, context, appWidgetId);
		setList(remoteViews, context, appWidgetId, day);

		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	void setupEvents(RemoteViews remoteViews, Context context, int appWidgetId) {
		PendingIntent pIntent;

		Intent menuIntent = new Intent(context, MyProvider.class);
		menuIntent.setAction(ACTION_MENU);
		menuIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		pIntent = PendingIntent.getBroadcast(context, appWidgetId, menuIntent,
				0);
		remoteViews.setOnClickPendingIntent(R.id.btnMenu, pIntent);

		Intent nextDayIntent = new Intent(context, MyProvider.class);
		nextDayIntent.setAction(ACTION_NEXT);
		nextDayIntent
				.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		pIntent = PendingIntent.getBroadcast(context, appWidgetId,
				nextDayIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnNextDay, pIntent);

		Intent prevDayIntent = new Intent(context, MyProvider.class);
		prevDayIntent.setAction(ACTION_PREVIOUS);
		prevDayIntent
				.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		pIntent = PendingIntent.getBroadcast(context, appWidgetId,
				prevDayIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnPrevDay, pIntent);

		Intent datePickerIntent = new Intent(context, DatePickerActivity.class);
		datePickerIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		pIntent = PendingIntent.getActivity(context, 0, datePickerIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnCalendar, pIntent);

		Intent todayIntent = new Intent(context, MyProvider.class);
		todayIntent.setAction(ACTION_TODAY);
		todayIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		pIntent = PendingIntent.getBroadcast(context, appWidgetId, todayIntent,
				0);
		remoteViews.setOnClickPendingIntent(R.id.btnCurrDay, pIntent);
	}

	void setList(RemoteViews rv, Context context, int appWidgetId, Day day) {
		Intent adapter = new Intent(context, MyService.class);
		adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		adapter.putExtra("day", day.ordinal());
		adapter.setData(Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME)));
		rv.setRemoteAdapter(R.id.lvList, adapter);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		RemoteViews widgetView = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		final String action = intent.getAction();

		if (action.equalsIgnoreCase(ACTION_MENU)) {
			Log.d(LOG_TAG, "MENU is clicked");

			try {
				ReadExcelTest excelTester = new ReadExcelTest(context
						.getFilesDir().getPath() + "/feit.xls");
				System.out.println("Data from the cell G10 is: "
						+ excelTester.getCellData(5, 9, 6));
				excelTester.printScheduleOfCourse(5, context.getFilesDir()
						.getPath() + "/excelContent.txt");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

//			int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//			Bundle extras = intent.getExtras();
//			if (extras != null) {
//				mAppWidgetId = extras.getInt(
//						AppWidgetManager.EXTRA_APPWIDGET_ID,
//						AppWidgetManager.INVALID_APPWIDGET_ID);
//			}
//			if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
//				AppWidgetManager appWidgetManager = AppWidgetManager
//						.getInstance(context);
//				show_buttons = !show_buttons;
//				updateVisibilityOfButtons(context, appWidgetManager,
//						mAppWidgetId, widgetView);
//			}
		} else if (action.equalsIgnoreCase(ACTION_NEXT)) {
			Log.d(LOG_TAG, "NEXT is clicked");

			int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mAppWidgetId = extras.getInt(
						AppWidgetManager.EXTRA_APPWIDGET_ID,
						AppWidgetManager.INVALID_APPWIDGET_ID);
			}
			if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				Day day = scheduler.getNextDay();
				widgetView.setTextViewText(R.id.tvTitle, scheduler.getDate(day));
				Log.d(LOG_TAG, "next day: " + scheduler.getDate(day));
				setList(widgetView, context, mAppWidgetId, day);

				appWidgetManager.updateAppWidget(mAppWidgetId, widgetView);
			}
		} else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
			Log.d(LOG_TAG, "PREV is clicked");

			int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mAppWidgetId = extras.getInt(
						AppWidgetManager.EXTRA_APPWIDGET_ID,
						AppWidgetManager.INVALID_APPWIDGET_ID);
			}
			if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				Day day = scheduler.getPrevDay();
				widgetView.setTextViewText(R.id.tvTitle, scheduler.getDate(day));
				Log.d(LOG_TAG, "prev day: " + scheduler.getDate(day));
				setList(widgetView, context, mAppWidgetId, day);

				appWidgetManager.updateAppWidget(mAppWidgetId, widgetView);
			}
		} else if (action.equalsIgnoreCase(ACTION_CALENDAR)) {
			Log.d(LOG_TAG, "CALENDAR is clicked");

			int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mAppWidgetId = extras.getInt(
						AppWidgetManager.EXTRA_APPWIDGET_ID,
						AppWidgetManager.INVALID_APPWIDGET_ID);
			}
			if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				
				try {
					String date = extras.getString("day");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Log.d(LOG_TAG, "date to show is " + date);
					Date pickedDate = sdf.parse(date);
					Day day = scheduler.getDay(pickedDate);
					widgetView.setTextViewText(R.id.tvTitle, scheduler.getDate(day));
					Log.d(LOG_TAG, "picked day: " + scheduler.getDate(day));
					setList(widgetView, context, mAppWidgetId, day);

					appWidgetManager.updateAppWidget(mAppWidgetId, widgetView);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else if (action.equalsIgnoreCase(ACTION_TODAY)) {
			Log.d(LOG_TAG, "TODAY is clicked");

			int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mAppWidgetId = extras.getInt(
						AppWidgetManager.EXTRA_APPWIDGET_ID,
						AppWidgetManager.INVALID_APPWIDGET_ID);
			}
			if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				Day day = scheduler.getToday();
				widgetView.setTextViewText(R.id.tvTitle, scheduler.getDate(day));
				setList(widgetView, context, mAppWidgetId, day);

				appWidgetManager.updateAppWidget(mAppWidgetId, widgetView);
			}
		}

		super.onReceive(context, intent);
	}

	void updateVisibilityOfButtons(Context context,
			AppWidgetManager appWidgetManager, int widgetID,
			RemoteViews widgetView) {
		int visibility = (show_buttons) ? View.VISIBLE : View.INVISIBLE;
		widgetView.setViewVisibility(R.id.btnCalendar, visibility);
		widgetView.setViewVisibility(R.id.btnCurrDay, visibility);
		widgetView.setViewVisibility(R.id.btnNextDay, visibility);
		widgetView.setViewVisibility(R.id.btnPrevDay, visibility);

		appWidgetManager.updateAppWidget(widgetID, widgetView);
	}

}
