package com.mawujun.inventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {
	static SimpleDateFormat yyyyMMdd_formater=new SimpleDateFormat("yyyyMMdd");
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		long diff = yyyyMMdd_formater.parse("20150715").getTime() - yyyyMMdd_formater.parse("20150711").getTime();
		long day_length = diff / (1000 * 60 * 60 * 24);
		System.out.println(day_length);
	}

}
