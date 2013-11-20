package cornell.drinkingapp;

import java.util.ArrayList;
import java.util.Date;

import cornell.drinkingapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	private Button tracking, assessment, visualize, settings, resources;
	private Intent goToThisPage;
	private DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//get database
		db = new DatabaseHandler(this);
		
		setContentView(R.layout.menu);
		tracking = (Button) findViewById(R.id.bMenuTracking);
		assessment = (Button) findViewById(R.id.bMenuAssessment);
		visualize = (Button) findViewById(R.id.bMenuVisualize);
		settings = (Button) findViewById(R.id.bMenuSettings);
		//resources = (Button) findViewById(R.id.bMenuResources);

		tracking.setOnClickListener(this);
		assessment.setOnClickListener(this);
		visualize.setOnClickListener(this);
		settings.setOnClickListener(this);
		//resources.setOnClickListener(this);

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
			ArrayList<DatabaseStore> drank = (ArrayList<DatabaseStore>)db.getVarValuesForDay("drank_last_night", date);
			if (drank == null) {
				Intent drink_ques = new Intent(this, DailySurvey1.class);
				startActivityForResult(drink_ques,2);
			} else {
				ArrayList<DatabaseStore> assess = (ArrayList<DatabaseStore>)db.getVarValuesForDay("drink_assess", date);
				goToThisPage = new Intent(MainMenu.this, Assessment.class);
				startActivity(goToThisPage);
				if(assess == null && drank.get(0).value.equals("True")){

				
				/*this is causing the app to crash.
					Intent drink_review = new Intent(this, DrinkReview.class);
					startActivity(drink_review);
					*/
					Intent drink_assess = new Intent(this, DrinkAssessment.class);
					startActivity(drink_assess);
				}
			}
			break;
		case R.id.bMenuVisualize:
			goToThisPage = new Intent(MainMenu.this, VisualizeMenu.class);
			startActivity(goToThisPage);
			break;
		case R.id.bMenuSettings:
			goToThisPage = new Intent(MainMenu.this, Settings.class);
			startActivity(goToThisPage);
			break;
			/*
		case R.id.bMenuResources:
			goToThisPage = new Intent(MainMenu.this, Resources.class);
			startActivity(goToThisPage);
			break;
			*/
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Date date = new Date();
		ArrayList<DatabaseStore> drank = (ArrayList<DatabaseStore>)db.getVarValuesForDay("drank_last_night", date);
		goToThisPage = new Intent(MainMenu.this, Assessment.class);
		startActivity(goToThisPage);
		
		if (drank.get(0).value.equals("True")){
			Intent how_many = new Intent(this, DrinkAssessment.class);
			startActivity(how_many);
		}		
	}
	
}
