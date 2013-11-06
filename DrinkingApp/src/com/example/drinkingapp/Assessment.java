package com.example.drinkingapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class Assessment extends ListActivity {
	//TODO: Clean this up and make it more manageable
	// repetitive, but makes it easier for the programmers to add onto the list
	ArrayList<String> surveys = new ArrayList<String>();
	ArrayList<String> surveys_names = new ArrayList<String>();
	String surveyList[] = { "DailySurvey1", "DailySurvey2", "DailySurvey3",
			"DailySurvey4", "DailySurvey6", "DailySurveySleep",
			"DailySurveyExercise", "DailySurveyProductivity",
			"DailySurveySocial" };
	
	String surveyDisplay[] ={"Drink?", "Overall", "Logistics", "Food"};
	String placement;
	Boolean sleepSelected, exerciseSelected, productivitySelected,
			socialSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		for (int n = 0; n < surveyList.length; n++) {
			surveys.add(surveyList[n]);
		}
		// gets rid of the evaluation categories if users unchecked it.
		SharedPreferences getPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		Boolean sleepSelected = getPreference.getBoolean("sleepInEvaluation",
				true);
		Boolean exerciseSelected = getPreference.getBoolean(
				"exerciseInEvaluation", true);
		Boolean productivitySelected = getPreference.getBoolean(
				"productivityInEvaluation", true);
		Boolean socialSelected = getPreference.getBoolean("socialInEvaluation",
				true);

		if (!sleepSelected) {
			surveys.remove("DailySurveySleep");
		}
		if (!exerciseSelected) {
			surveys.remove("DailySurveyExercise");
		}
		if (!productivitySelected) {
			surveys.remove("DailySurveyProductivity");
		}
		if (!socialSelected) {
			surveys.remove("DailySurveySocial");
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);// full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setListAdapter(new ArrayAdapter<String>(Assessment.this,
				android.R.layout.simple_list_item_checked, surveys));// have to
																		// create
																		// for
																		// list

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		CheckedTextView textview = (CheckedTextView) v;
		textview.setChecked(true);
		placement = surveys.get(position);

		try {
			Class ourClass = Class.forName("com.example.drinkingapp."
					+ placement);
			Intent goToSurvey = new Intent(this, ourClass);
			startActivity(goToSurvey);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
