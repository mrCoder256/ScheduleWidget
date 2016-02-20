package ua.chntu.sheduler;

import java.util.ArrayList;

import ua.chntu.sheduler.DateWrapper.Day;
import ua.chntu.sheduler.R;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class MyFactory implements RemoteViewsFactory {

	final static String LOG_TAG = "myLogs";
  
	private ArrayList<Classes> lectures;
	private Context context;
	private final Day day;

	MyFactory(Context ctx, Intent intent) {
		context = ctx;
		day = Day.valueOf(intent.getIntExtra("day", 1));
		Log.d(LOG_TAG, "MyFactory.day " + day.name());
	}

	@Override
	public void onCreate() {
		ScheduleProvider scheduler = new ScheduleProvider();
		lectures = scheduler.getSchedule(day);
	}

	@Override
	public int getCount() {
		return lectures.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public RemoteViews getViewAt(int position) {
		Classes lecture = lectures.get(position);		
		RemoteViews rView = null;
		switch(lecture.getType()) {
			case Classes.LECTURE: {
	
				rView = new RemoteViews(context.getPackageName(), 
						R.layout.lecture);
	
				rView.setTextViewText(R.id.tvNumber, 
						"  " + String.valueOf(position + 1) + "  ");
				if (lecture.getLectureName1() != null)
					rView.setTextViewText(R.id.tvLectureName,
							lecture.getLectureName1());
				else rView.setTextViewText(R.id.tvLectureName, "");
				if (lecture.getLecturerAndHall1() != null)
					rView.setTextViewText(R.id.tvLecturerAndHall, 
							lecture.getLecturerAndHall1());
				else rView.setTextViewText(R.id.tvLecturerAndHall, "");
	
				return rView;
			}
			/*case Classes.PRAXIS: {
				rView = new RemoteViews(context.getPackageName(), 
						R.layout.praxis);
	
				rView.setTextViewText(R.id.tvNumber, 
						" " + String.valueOf(position + 1) + " ");
				if (lecture.getLectureName1() != null)
					rView.setTextViewText(R.id.tvLectureName1, 
							lecture.getLectureName1());
				else rView.setTextViewText(R.id.tvLectureName1, "");
				if (lecture.getLecturerAndHall1() != null)
					rView.setTextViewText(R.id.tvLectureAndHall1, 
							lecture.getLecturerAndHall1());
				else rView.setTextViewText(R.id.tvLectureAndHall1, "");
				if (lecture.getLectureName2() != null)
					rView.setTextViewText(R.id.tvLectureName2, 
							lecture.getLectureName2());
				else rView.setTextViewText(R.id.tvLectureName2, "");
				if (lecture.getLecturerAndHall2() != null)
					rView.setTextViewText(R.id.tvLectureAndHall2, 
							lecture.getLecturerAndHall2());
				else rView.setTextViewText(R.id.tvLectureAndHall2, "");
	
				return rView;
			}*/
		}
		return null;
	}

	@Override
	public void onDataSetChanged() {
		
	}

}