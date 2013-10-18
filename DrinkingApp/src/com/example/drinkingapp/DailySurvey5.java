package com.example.drinkingapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class DailySurvey5 extends Activity implements OnClickListener{

	Button finish;
	CheckBox option1,option2,option3,option4,option5,option6,option7,option8,option9,option10;
	EditText otherTop,otherBot;
	ArrayList<String> wordsTop=new ArrayList();
	ArrayList<String> wordsBot=new ArrayList();
	ArrayList<CheckBox> optionListTop=new ArrayList();
	ArrayList<CheckBox> optionListBot=new ArrayList();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.dailysurvey3);
		finish=(Button)findViewById(R.id.bDS5Finish);
		finish.setOnClickListener(this);
		option1=(CheckBox)findViewById(R.id.cbDS5Box1);
		option2=(CheckBox)findViewById(R.id.cbDS5Box2);
		option3=(CheckBox)findViewById(R.id.cbDS5Box3);
		option4=(CheckBox)findViewById(R.id.cbDS5Box4);
		option5=(CheckBox)findViewById(R.id.cbDS5Box5);
		option6=(CheckBox)findViewById(R.id.cbDS5Box6);
		option7=(CheckBox)findViewById(R.id.cbDS5Box7);
		option8=(CheckBox)findViewById(R.id.cbDS5Box8);
		option9=(CheckBox)findViewById(R.id.cbDS5Box9);
		option10=(CheckBox)findViewById(R.id.cbDS5Box10);
		
		optionListTop.add(option1);
		optionListTop.add(option2);
		optionListTop.add(option3);
		optionListTop.add(option4);
		optionListTop.add(option5);
		optionListTop.add(option6);
		//bottom survey
		optionListBot.add(option7);
		optionListBot.add(option8);
		optionListBot.add(option9);
		optionListBot.add(option10);
		
	}

	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		/*
		case R.id.bDS2Record:
			break;
			
			*/
		//checks which checklist is check and input 
		//that value in a string to pass onto the database function to parse
		case R.id.bDS5Finish:
			for (int x=0;x<optionListTop.size();x++){
				if(optionListTop.get(x).isChecked()){
					if (optionListTop.get(x).getId()==R.id.cbDS5Box6)
						wordsTop.add(otherTop.getText().toString());
					else
						wordsTop.add(optionListTop.get(x).getText().toString());
				}
			}
			for (int x=0;x<optionListTop.size();x++){
				if(optionListBot.get(x).isChecked()){
					if (optionListBot.get(x).getId()==R.id.cbDS5Box10)
						wordsBot.add(otherBot.getText().toString());
					wordsBot.add(optionListBot.get(x).getText().toString());
				}
			}
			//pass intent with two word lists attached
			break;
			
		}
		
	}



}
