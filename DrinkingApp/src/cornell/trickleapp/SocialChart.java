package cornell.trickleapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class SocialChart extends Activity{
	TimeBacGraph visual;
	ArrayList<String> groupName=new ArrayList<String>();
	ArrayList<Integer> hangoutCount=new ArrayList<Integer>();
	ArrayList<Double> bacLevel=new ArrayList<Double>();
	//ArrayList<TrendSliceItem> valuesLIst = new Array
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//mock data
		/*
		groupName.add("Friends");
		groupName.add("Family");
		groupName.add("Dog");
		groupName.add("Sirs");
		hangoutCount.add(3);
		hangoutCount.add(1);
		hangoutCount.add(5);
		hangoutCount.add(3);
		bacLevel.add(0.1);
		bacLevel.add(0.18);
		bacLevel.add(0.05);
		bacLevel.add(0.2);
		*/
		
		//ArrayList<TrendsSliceItem> vals = new ArrayList<TrendsSliceItem>();
		//vals.add(new TrendsSliceItem("F"));
		
		
		
		//visual = new TimeBacGraph(this,groupName,hangoutCount,bacLevel);
		//setContentView(visual);
		setContentView(visual);

	}

}
