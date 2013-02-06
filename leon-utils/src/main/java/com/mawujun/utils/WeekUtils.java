package com.mawujun.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class WeekUtils {
	
	public static void main(String[] args) {

//		int year = 2010;
//
//		int week = 1;
//
//		// ��2006-01-02λ��
//
//		Calendar c = new GregorianCalendar();
//
//		c.set(2010, Calendar.JANUARY, 2);
//
//		Date d = c.getTime();
//
//		System.out.println("current date = " + d);
//
//		System.out.println("getWeekOfYear = " + getWeekOfYear(new Date()));
//
//		System.out.println("getMaxWeekNumOfYear = " + getMaxWeekNumOfYear(year));
//
//		System.out.println("getFirstDayOfWeek = "
//				+ getFirstDayOfWeek(year, week));
//
//		System.out
//				.println("getLastDayOfWeek = " + getLastDayOfWeek(year, week));
//
//		System.out.println("getFirstDayOfWeek = " + getFirstDayOfWeek(d));
//
//		System.out.println("getLastDayOfWeek = " + getLastDayOfWeek(d));
		
		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, 2009);

		c.set(Calendar.MONTH, Calendar.DECEMBER);

		c.set(Calendar.DATE, 29);
		
		Date date=c.getTime();
		date=new Date();
		
		System.out.println("getLastDayOfWeek = " + getMaxNaturalWeekNumOfYear(2010));
		//System.out.println("getLastDayOfWeek = " + getFirstDayOfNaturalWeek(2010,52));
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String aa=format.format(getFirstDayOfNaturalWeek(2010,52));
		
		System.out.println(aa);

	}

	/**
	 * 
	 * ȡ�õ�ǰ��������ĵڼ���
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮�����2005���һ�ܵĿ�ʼ��
	 * ��������������20050101�����ﷵ�ص���0����ʾ������ǰһ������ˣ�������52
	 * @param date
	 * 
	 * @return
	 */

	public static int getWeekOfYear(Date date) {
		if(date==null){
			date=new Date();
		}
		
		//Calendar c = new GregorianCalendar();
		Calendar c = Calendar.getInstance();
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(7);

		c.setTime(date);
		
		//���ǰ���ǵ�һ���£����ҷ��ص�week����5��ʾ ���ص���ʱǰһ������һ��,��������ʾΪ0������ʾ��ǰһ��
		//����һ�������ǿ���ģ����һ�����ڹ�ȥ��
		if(c.get(Calendar.MONTH)==Calendar.JANUARY && c.get(Calendar.WEEK_OF_YEAR)>5){
			return 0;
		}

		return c.get(Calendar.WEEK_OF_YEAR);

	}
	
	/**
	 * 
	 * ȡ�õ�ǰ��������ĵڼ���,��ȡ������Ȼ��
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������Ϊ��һ�ܣ��� �ܸ���ʱ���ڼ�
	 * -- ����20050101Ϊ��������20041226��20050102Ϊ2005���һ�� ��20050103��ʼΪ��2��
	 * ��������������20041227�����ﷵ�ص���0����ʾ�����ǵڶ�������ˣ�������1��
	 * @param date
	 * 
	 * @return
	 */

	public static int getNaturalWeekOfYear(Date date) {
		if(date==null){
			date=new Date();
		}
		
		//Calendar c = new GregorianCalendar();
		Calendar c = Calendar.getInstance();
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);

		c.setTime(date);
		
		//���ǰ����12�£����ҷ��ص�weekС��1�����ʾ������ڹ�Ϊ�ڶ���ĵ�һ������
		//������һ�������ǿ���ģ������һ�����ڹ�����
		if(c.get(Calendar.MONTH)==Calendar.DECEMBER && c.get(Calendar.WEEK_OF_YEAR)<2){
			//throw new IllegalArgumentException("��ǰ���ڣ��Ѿ������ڽ�����ܵ·�Χ��");
			return 0;
		}

		return c.get(Calendar.WEEK_OF_YEAR);

	}
	
	/**
	 * ��ȡĳ����Ȼ�ܵ�1�ܵĿ�ʼ���ڣ�����20050101Ϊ�������ǵ�1�ܵ¿�ʼ������20041227 ����������20050102��
	 * �ڶ��ܵĿ�ʼ������20050103�������ȡ�ľ�����Ȼ�ܵ�1�ܵĿ�ʼ����
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfFirstNaturalWeek(int year) {

		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);
		
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);
		
		c.setMinimalDaysInFirstWeek(1);

		Calendar cal = (GregorianCalendar) c.clone();

		return getFirstDayOfWeek(cal.getTime());
	}
	
	/**
	 * ��ȡĳ����Ȼ�ܵ�1�ܵĿ�ʼ���ڣ�����20050101Ϊ�������ǵ�0�ܵ¿�ʼ������<b>20050101</b> ����������<b>20050102</b>��
	 * �ڶ��ܵĿ�ʼ������20050103�������ȡ�ľ��ǵ�1�ܵĿ�ʼ����,<b>������</b>
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfFirstNaturalWeekNoYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);
		
		return c.getTime();
	}
	
	/**
	 * �ж��ƶ���ݵ�1��1��  �ǲ�������һ
	 * @param year
	 * @return
	 */
	public static boolean isFirstDayIsMonday(int year) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY;//�жϵ��� ��һ�� �ǲ������� һ
		
	}
	
	/**
	 * �ж��ƶ���ݵ�1��1��  �ǲ�������һ
	 * @param date  
	 * @return
	 */
	public static boolean isFirstDayIsMonday(Date date) {
		if(date==null){
			date=new Date();
		}
		Calendar c = new GregorianCalendar();
		//c.set(Calendar.YEAR, year);
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY;//�жϵ��� ��һ�� �ǲ������� һ
		
	}

	
	/**
	 * ��ȡĳ����Ȼ�ܵ�1�ܵĽ������ڣ�����20050101Ϊ�������ǵ�1�ܵ¿�ʼ������20041227 ����������20050102��
	 * �ڶ��ܵĿ�ʼ������20050103�������ȡ�ľ�����Ȼ�ܵ�1�ܵĽ�������
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfFirstNaturalWeek(int year) {
		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);
		
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);

		Calendar cal = (GregorianCalendar) c.clone();

		//cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());

	}
	
	

	/**
	 * 
	 * �õ�ĳһ���ܵ�����
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
	 * 
	 * @param year
	 * 
	 * @return
	 */

	public static int getMaxWeekNumOfYear(int year) {

		Calendar c = new GregorianCalendar();

		//���ø�������һ��
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());

	}
	
	/**
	 * 
	 * �õ�ĳһ���ܵ�����
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������Ϊ��һ��
	 * -- ����20050101Ϊ��������20050103֮����ǵڶ��ܵĿ�ʼ��
	 * ������һ�������ǿ���ģ������һ�����ڹ�����
	 * @param year
	 * 
	 * @return
	 */

	public static int getMaxNaturalWeekNumOfYear(int year) {

		Calendar c = new GregorianCalendar();

		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);
		
		//������һ�첻��������Ļ����ͱ��ʾ�������ǿ���ģ�����������ǹ������
		if(c.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
			c.add(Calendar.DATE, -7);
		}
		System.out.println("aaaaaaaaaa:"+c.getTime());

		return c.get(Calendar.WEEK_OF_YEAR);
		//return getNaturalWeekOfYear(c.getTime());

	}
	

	/**
	 * 
	 * �õ�ĳ��ĳ�ܵĵ�һ��
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
	 * 
	 * @param year
	 * 
	 * @param week ���������ó��ģ��ܵ�����
	 * 
	 * @return
	 */

	public static Date getFirstDayOfWeek(int year, int week) {

		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();

		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());

	}
	
	public static Date getFirstDayOfNaturalWeek(int year, int week) {

		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();

		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);
		
		if(!isFirstDayIsMonday(c.getTime())){
			cal.add(Calendar.DATE, (week-1) * 7);
		} else {
			cal.add(Calendar.DATE, week * 7);
		}

		
		
		return getFirstDayOfNaturalWeek(cal.getTime());

	}
	
//	/**
//	 * 
//	 * �õ�ĳ��ĳ�ܵĵ�һ��
//	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
//	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
//	 * 
//	 * @param year
//	 * 
//	 * @param week ���������ó��ģ��ܵ�����
//	 * 
//	 * @return
//	 */
//
//	public static Date getFirstDayOfWeek(Date date, int week) {
//
//		Calendar c = new GregorianCalendar();
//		c.setTime(date);
//
////		c.set(Calendar.YEAR, year);
////
//		c.set(Calendar.MONTH, Calendar.JANUARY);
//
//		c.set(Calendar.DATE, 1);
//
//		Calendar cal = (GregorianCalendar) c.clone();
//
//		cal.add(Calendar.DATE, week * 7);
//
//		return getFirstDayOfWeek(cal.getTime());
//
//	}

	/**
	 * 
	 * �õ�ĳ��ĳ�ܵ����һ��
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
	 * 
	 * @param year
	 * 
	 * @param week ���������ó��ģ��ܵ�����
	 * 
	 * @return
	 */

	public static Date getLastDayOfWeek(int year, int week) {
		if(week>getMaxWeekNumOfYear(year)){
			throw new IllegalArgumentException("���ڳ����˱�����������");
		}

		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();

		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());

	}
	
	public static Date getLastDayOfNaturalWeek(int year, int week) {
		if(week>getMaxNaturalWeekNumOfYear(year)){
			throw new IllegalArgumentException("���ڳ����˱�����������");
		}

		Calendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, year);

		c.set(Calendar.MONTH, Calendar.JANUARY);

		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();

		cal.add(Calendar.DATE, (week-1) * 7);

		return getLastDayOfNaturalWeek(cal.getTime());

	}
	

	/**
	 * 
	 * ȡ�õ�ǰ���������ܵĵ�һ��
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
	 * 
	 * @param date
	 * 
	 * @return
	 */

	public static Date getFirstDayOfWeek(Date date) {

		Calendar c = new GregorianCalendar();

		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

		return c.getTime();
	}
	
	public static Date getFirstDayOfNaturalWeek(Date date) {

		Calendar c = new GregorianCalendar();

		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);
		
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

		return c.getTime();
	}

	/**
	 * 
	 * ȡ�õ�ǰ���������ܵ����һ��
	 * ����һ����������һ�ܣ���ÿ��ĵ�һ������һΪ��һ��
	 * -- ����20050101Ϊ������������iw���㷨��ǰ���52�ܣ���20050103֮����ǵ�һ�ܵĿ�ʼ��
	 * 
	 * @param date
	 * 
	 * @return
	 */

	public static Date getLastDayOfWeek(Date date) {

		Calendar c = new GregorianCalendar();

		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		return c.getTime();
	}
	
	public static Date getLastDayOfNaturalWeek(Date date) {

		Calendar c = new GregorianCalendar();

		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);

		
		
		//����һ���ڵĵ�һ������һ�죻���磬��������һ���� SUNDAY�����ڷ�����һ���� MONDAY��
		c.setFirstDayOfWeek(Calendar.MONDAY);
		//����һ���е�һ����������������������磬������һ�����ڰ�һ���һ���µĵ�һ�죬��ʹ��ֵ 1 ���ô˷�����
		c.setMinimalDaysInFirstWeek(1);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		return c.getTime();
	}



}
