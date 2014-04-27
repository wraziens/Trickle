package cornell.trickleapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import cornell.trickleapp.R;
import cornell.trickleapp.R.id;
import cornell.trickleapp.R.layout;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity implements OnClickListener {

	private Button tracking, assessment, visualize, settings, resources,
			sendData,badges,menu2;
	private Intent goToThisPage;
	private DatabaseHandler db;
	AlarmManager alarmManager;
	PowerManager.WakeLock mWakeLock;
	NotificationManager nm;
	static final int uniqueID=1991;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// get database
		
		nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		//setAlarm();
		db = new DatabaseHandler(this);
		setContentView(R.layout.menu);

		tracking = (Button) findViewById(R.id.bMenuTracking);
		assessment = (Button) findViewById(R.id.bMenuAssessment);
		visualize = (Button) findViewById(R.id.bMenuVisualize);
		settings = (Button) findViewById(R.id.bMenuSettings);
		sendData = (Button) findViewById(R.id.bSendData);
		badges= (Button) findViewById(R.id.bMenuBadges);
		menu2= (Button) findViewById(R.id.bMenu_2);
		tracking.setOnClickListener(this);
		assessment.setOnClickListener(this);
		visualize.setOnClickListener(this);
		settings.setOnClickListener(this);
		sendData.setOnClickListener(this);
		badges.setOnClickListener(this);
		menu2.setOnClickListener(this);
		// resources.setOnClickListener(this);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		Boolean checkSurveyed = getPrefs.getBoolean("hints", true);
		if (checkSurveyed) {
			Intent openHint = new Intent(this, MainMenuTutorial.class);
			startActivity(openHint);
		}
		sendData.setVisibility(4);
		nm.cancel(uniqueID);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bMenuTracking:
			// goToThisPage = new Intent(Menu.this,Class.Tracking);
			goToThisPage = new Intent(MainMenu.this, DrinkCounter.class);
			startActivity(goToThisPage);
			break;
		case R.id.bMenuAssessment:
			Date date = new Date();
			ArrayList<DatabaseStore> drank = (ArrayList<DatabaseStore>) db
					.getVarValuesForDay("drank_last_night", date);
			// goToThisPage = new Intent(MainMenu.this, Assessment.class);
			// startActivity(goToThisPage);
			if (drank == null) {
			} else {
				ArrayList<DatabaseStore> assess = (ArrayList<DatabaseStore>) db
						.getVarValuesForDay("drink_assess", date);
				if (assess == null && drank.get(0).value.equals("True")) {
					Intent drink_assess = new Intent(MainMenu.this,
							DrinkAssessment.class);
					startActivityForResult(drink_assess, 4);
				} else {
					Intent i = new Intent(this, Assessment.class);
					startActivity(i);
				}
			}

			// }
			break;
		case R.id.bMenuVisualize:
			goToThisPage = new Intent(MainMenu.this, VisualizeMenu.class);
			startActivity(goToThisPage);
			break;
		case R.id.bMenuSettings:
			goToThisPage = new Intent(MainMenu.this, Settings.class);
			startActivity(goToThisPage);
			break;
		case R.id.bSendData:

			DatabaseHandler db = new DatabaseHandler(this);
			List<DatabaseStore> list = db.dataDump();
			sendData(list);
			break;
		case R.id.bMenuBadges:
			goToThisPage = new Intent(MainMenu.this, Badges.class);
			startActivity(goToThisPage);
			break;
		case R.id.bMenu_2:
			goToThisPage = new Intent(MainMenu.this, MainMenu3.class);
			startActivity(goToThisPage);
			break;
		/*
		 * case R.id.bMenuResources: goToThisPage = new Intent(MainMenu.this,
		 * Resources.class); startActivity(goToThisPage); break;
		 */
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2) {
			Date date = new Date();
			ArrayList<DatabaseStore> drank = (ArrayList<DatabaseStore>) db
					.getVarValuesForDay("drank_last_night", date);
			if (drank.get(0).value.equals("True")) {
				Intent how_many = new Intent(this, DrinkAssessment.class);
				startActivityForResult(how_many, 4);
			} else {
				goToThisPage = new Intent(MainMenu.this, Assessment.class);
				startActivity(goToThisPage);
			}
		} else if (resultCode == 3) {
			goToThisPage = new Intent(MainMenu.this, Assessment.class);
			startActivity(goToThisPage);
		} else if (resultCode == 4) {
			Date date = new Date();
			ArrayList<DatabaseStore> track = (ArrayList<DatabaseStore>) db
					.getVarValuesForDay("tracked", date);
			if (track != null) {
				// goToThisPage = new Intent(MainMenu.this, Assessment.class);
				// startActivity(goToThisPage);
				Intent drink_review = new Intent(this, DrinkReview.class);
				startActivityForResult(drink_review, 3);
			} else {
				goToThisPage = new Intent(MainMenu.this, Assessment.class);
				startActivity(goToThisPage);
			}
		}
	}

	private void sendData(List<DatabaseStore> dataList) {
		if (dataList != null) {

			String android_id = Secure.getString(getBaseContext()
					.getContentResolver(), Secure.ANDROID_ID);

			Parse.initialize(this, "pzrwzzF69gXSQrxr9gmfhWVQq3it1UrLFxCbPyUw",
					"8dZZu0sRje5F4K31FwAmYXbdSmkCOTZvUIfQo1N1");

			ParseUser.enableAutomaticUser();
			ParseACL defaultACL = new ParseACL();

			// If you would like all objects to be private by default, remove
			// this line.
			defaultACL.setPublicReadAccess(true);

			ParseACL.setDefaultACL(defaultACL, true);

			DatabaseStore item;
			for (int i = 0; i < dataList.size(); i++) {
				item = dataList.get(i);
				ParseObject researchObject = new ParseObject("research");
				researchObject.put("variableName", item.variable);
				researchObject.put("varValue", item.value);
				researchObject.put("dateValue", item.date);
				researchObject.put("userId", android_id);
				researchObject.put("groupNo", 2);
				researchObject.saveInBackground();
			}

			Toast.makeText(getApplicationContext(), "Your Data Has Been Sent",
					Toast.LENGTH_SHORT).show();
			sendData.setVisibility(4);
		} else {
			Toast.makeText(getApplicationContext(), "No Data Sent",
					Toast.LENGTH_SHORT).show();
		}
	}



}