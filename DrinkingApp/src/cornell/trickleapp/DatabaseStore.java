package cornell.trickleapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class DatabaseStore {
	public String value;
	public String variable;
	public Date date;
	public String type;
	public Integer day;
	public Integer month;
	public Integer year;
	public Integer hour;
	public Integer minute;
	public String time;
	public String day_week;
	public int time_seconds;
	//Should not be explicitly used but rather should use the below static methods
	//when creating a DatabaseStore to preserve consistency among the Question Types
	public DatabaseStore(String variable, String value, 
			Date date, String ques_type){
		this.type = ques_type;
		this.value = value;
		this.date = date;
		this.variable=variable;
		
		//for easy access, convert the date attributes
		SimpleDateFormat day_ft = new SimpleDateFormat("dd", Locale.US);
		SimpleDateFormat month_ft = new SimpleDateFormat("MM", Locale.US);
		SimpleDateFormat year_ft = new SimpleDateFormat("yyyy", Locale.US);
		SimpleDateFormat day_week_ft = new SimpleDateFormat("E", Locale.US);
		SimpleDateFormat hour_ft = new SimpleDateFormat("HH", Locale.US);
		SimpleDateFormat minute_ft = new SimpleDateFormat("mm",Locale.US);
		SimpleDateFormat second_ft = new SimpleDateFormat("ss", Locale.US);
		SimpleDateFormat time_ft = new SimpleDateFormat("HH:mm:ss", Locale.US);
		
		this.day = Integer.parseInt(day_ft.format(date));
		this.month = Integer.parseInt(month_ft.format(date));
		this.year = Integer.parseInt(year_ft.format(date));
		this.day_week = day_week_ft.format(date);
		this.time = time_ft.format(date);
		this.hour = Integer.parseInt(hour_ft.format(date));
		this.minute=Integer.parseInt(minute_ft.format(date));
		this.time_seconds = this.hour * 3600 + this.minute * 60 + Integer.parseInt(second_ft.format(date));
	}
	
	public void setDate(Date date){
		//for easy access, convert the date attributes
		SimpleDateFormat day_ft = new SimpleDateFormat("dd", Locale.US);
		SimpleDateFormat month_ft = new SimpleDateFormat("MM", Locale.US);
		SimpleDateFormat year_ft = new SimpleDateFormat("yyyy", Locale.US);
		SimpleDateFormat day_week_ft = new SimpleDateFormat("E", Locale.US);
		SimpleDateFormat hour_ft = new SimpleDateFormat("HH", Locale.US);
		SimpleDateFormat minute_ft = new SimpleDateFormat("mm",Locale.US);
		SimpleDateFormat second_ft = new SimpleDateFormat("ss", Locale.US);
		SimpleDateFormat time_ft = new SimpleDateFormat("HH:mm:ss", Locale.US);
		
		this.date = date;
		this.day = Integer.parseInt(day_ft.format(date));
		this.month = Integer.parseInt(month_ft.format(date));
		this.year = Integer.parseInt(year_ft.format(date));
		this.day_week = day_week_ft.format(date);
		this.time = time_ft.format(date);
		this.hour = Integer.parseInt(hour_ft.format(date));
		this.minute=Integer.parseInt(minute_ft.format(date));
		this.time_seconds = this.hour * 3600 + this.minute * 60 + Integer.parseInt(second_ft.format(date));
	}
	
	public static ArrayList<DatabaseStore> sortByTime(List<DatabaseStore> store){
		ArrayList<DatabaseStore> sorted = new ArrayList<DatabaseStore>();
		
		if (store.size() > 1) {
			sorted.add(store.remove(0));
			for (int i =0; i< store.size(); i++){
				Date to_insert = store.get(i).date;
				boolean inserted = false;
				for (int j = 0; j< sorted.size(); j++){
					if(to_insert.before(sorted.get(j).date)){
						sorted.add(j, store.get(i));
						inserted=true;
						break;
					}
				}
				if (!inserted){
					sorted.add(store.get(i));
				}
			}
		}else if(store.size() == 1 ){
			return (ArrayList<DatabaseStore>)store;
		}
		return sorted;
	}
	
	public static Date getDelayedDate(){
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR_OF_DAY, -6);
		date = gc.getTime();
		return date;
	}
	
	public static Date getYesterday(){
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR_OF_DAY, -24);
		date = gc.getTime();
		return date;
	}
	
	public static Date getDelayedDateYesterday(){
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR_OF_DAY, -24);
		gc.add(Calendar.HOUR_OF_DAY, -6);
		date = gc.getTime();
		return date;
	}
	
	public static DatabaseStore DatabaseSliderStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Slider" );
	}
	
	public static DatabaseStore DatabaseTextStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Text" );
	}	
	
	public static DatabaseStore DatabaseIntegerStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Integer" );
	}
	
	public static DatabaseStore DatabaseDoubleStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Double" );
	}
	
	public static DatabaseStore DatabaseMultichoiceStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Multi" );
	}
	
	public static DatabaseStore DatabaseRadioStore(String variable, String value, Date date){
		return new DatabaseStore(variable, value, date, "Radio" );
	}
	
	public static DatabaseStore DatabaseDateStore(String variable, Date dateVal, Date date){
		SimpleDateFormat date_ft = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss", Locale.US);
		String dateStr = date_ft.format(dateVal);
		return new DatabaseStore(variable, dateStr, date, "Date");				
	}
	
	public static DatabaseStore FromDatabase(String variable, String value, Date date, 
			String type){
		return new DatabaseStore(variable, value, date, type);
	}
	
	public static Date retrieveDate(String dateStr) {
		try{
			Date date = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss", Locale.US).parse(dateStr);
			return date;
		}catch(ParseException pe){
			System.out.println("Cannot parse string.");
			return null;
		}
	}
	 
	public static Date GetDate(Integer month, Integer day, 
			Integer year, String time)throws ParseException {
		String date_string = year.toString() + "-" + month.toString() + "-" +
			day.toString() + " " + time;
		Date date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(date_string);
		return date;
	}
}
