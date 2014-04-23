package com.mawujun.utils.help;



import org.apache.commons.lang.StringUtils;

import com.mawujun.utils.ArrayUtils;


/**
 * 用于生产0001-0001-0002这种方式的数据
 * 是使用91进制
 * @author mawujun
 *
 */
public class ReportCodeHelper {
	//用~进行分割，这个值最大，他的值是126，字符的范围是33-126，
	//我们只取35-125的数据,从#开始，到~结束，，还排除了!和",因为双引号会引出莫名其妙的问题
	protected final static String sperator="~";
	//protected final static String ="~";
	private  static int min=97;
	protected final static int max=125;
	private static String minStr=(char)min+"";
	
	/**
	 * 产生的标准是每个节点有3位符号
	 * 
	 * @param baseCode
	 * @return
	 */
	public static String generate3(String baseCode){
//		if(baseCode==null || "".equals(baseCode.trim())){
//			baseCode=minStr+minStr+minStr;
//			return baseCode;
//		}
////		
////		for(int i=33;i<=126;i++){
////			System.out.println((char)i+"");
////		}
//		
//		String codes[]=baseCode.split(sperator);
//
//		String lastCode=codes[codes.length-1];
//		String resultsCode="";
//		boolean jinzhi=true;//是否进位,默认就是进一位，
//		for(int i=lastCode.length()-1;i>=0;i--){
//			int codeInt=lastCode.charAt(i);
//			if(codeInt<min || codeInt>max){
//				throw new ArithmeticException("数据不在范围内，请确认");
//			}
//			if(jinzhi){
//				if(codeInt==max){
//					codeInt=min;//变回最小，进制加1
//					jinzhi=true;
//				} else	if(codeInt<max){
//					codeInt++;
//					jinzhi=false;
//				}
//			}
//			//resultsCodes[codes.length-i-1]=(char)codeInt+"";
//			resultsCode=(char)codeInt+resultsCode;
//			
//		}
//		codes[codes.length-1]= resultsCode;
//		//System.out.println(lastCode);
//		if((minStr+minStr+minStr).equals(resultsCode)){
//			throw new ArithmeticException("节点个数超出范围了，一个循环已经结束,请重试!");
//		}
//		
//		return ArrayUtils.toString(codes, sperator);
		
		return generate(baseCode,3);
	}
	public static String generateFirstChildCode3(String baseCode){	
		return generateFirstChildCode(baseCode,3);
	}
	/**
	 * 获取下一节点的第一个值
	 * @param baseCode
	 * @param len
	 * @return
	 */
	public static String generateFirstChildCode(String baseCode,int len){	
		//return generate(baseCode+sperator+getMinCode(len),len);
		return baseCode+sperator+getMinCode(len);
	}
	private static String getMinCode(int len){
		return StringUtils.leftPad("", len, minStr);
	}
	/**
	 * len最好大于等于basecode中每个节点的位数，否则使用默认的长度忽略len
	 * 层次的深度是通过总长度进行限制的，层次的分隔符是：'~'.增加的是最后一个层次的节点数+1,其他层次的节点数不变，采用91进制
	 * @param baseCode
	 * @param len 指定每一节的位数,会把每一节的位数扩充到len指定的位数
	 * @return
	 */
	public static String generate(String baseCode,int len){
		if(baseCode==null || "".equals(baseCode.trim())){
			baseCode=getMinCode(len);
			return baseCode;
		}
		String codes[]=baseCode.split(sperator);
		for(int i=0;i<codes.length;i++) {
			if(codes[i].length()<len){
				codes[i]=StringUtils.leftPad(codes[i], len, minStr);
			}
		}
		
		String lastCode=codes[codes.length-1];
		String resultsCode="";
		boolean jinzhi=true;//是否进位,默认就是进一位，
		for(int i=lastCode.length()-1;i>=0;i--){
			int codeInt=lastCode.charAt(i);
			if(codeInt<min || codeInt>max){
				throw new ArithmeticException("数据不在范围内，请确认");
			}
			if(jinzhi){
				if(codeInt==max){
					codeInt=min;//变回最小，进制加1
					jinzhi=true;
				} else	if(codeInt<max){
					codeInt++;
					jinzhi=false;
				}
			}
			//resultsCodes[codes.length-i-1]=(char)codeInt+"";
			resultsCode=(char)codeInt+resultsCode;
			
		}
		codes[codes.length-1]= resultsCode;
		//System.out.println(lastCode);
		if((minStr+minStr+minStr).equals(resultsCode)){
			throw new ArithmeticException("节点个数超出范围了，一个循环已经结束,请重试!");
		}
		
		return ArrayUtils.toString(codes, sperator);
	}
	public static int getMin() {
		return min;
	}
	public static void setMin(int min) {
		ReportCodeHelper.min = min;
		minStr=(char)min+"";
	}
	public static String getSperator() {
		return sperator;
	}

}
