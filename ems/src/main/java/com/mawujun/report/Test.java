package com.mawujun.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mawujun.utils.StringUtils;

public class Test {

	public static void main(String[] args) throws ParseException {
//		// TODO Auto-generated method stub
//		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
//		int month=11;
//		Calendar cal=Calendar.getInstance();
//		cal.set(Calendar.MONTH, month);
//		cal.set(cal.get(Calendar.YEAR),month, 1, 0, 0, 0);
//		Date date_start=cal.getTime();
//		
//		int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		cal.set( cal.get(Calendar.YEAR),month, MaxDay, 23, 59, 59);
//		Date date_end=cal.getTime();
//		
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//		  System.out.println(sdf.format(date_start)); 
//		  System.out.println(sdf.format(date_end)); 
		//System.out.println((1/3)d);
		Calendar cal=Calendar.getInstance();
		String nowmonth_in=cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		cal.add(Calendar.MONTH, -1);
		String lastmonth_in=cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
	}

}
