package com.mawujun.utils.page;

/**
 * LIKEEND 表示是尾部匹配"%哈哈哈"，LIKESTART反之，LIKE是首尾都有百分号,likeExact是严格的like，但是可以自己带%
 * like 的百分号怎么带上，请看WhereInfo，如果用like的话，也可以自己写上百分号
 * @author mawujun
 *
 */
public enum Operation {
	//还有其他的，请看Restrictions
	EQ("="), LT("<"), GT(">"), LE("<="), GE(">="),ISNULL("is null"),ISNOTNULL("is not null"),
	LIKE("like"),LIKESTART("likestart"),LIKEEND("likeend"),ILIKE("ilike"),ILIKESTART("ilikestart"),ILIKEEND("ilikeend")
	,BETWEEN("between"),IN("in");
	
	private final String symbol;
	private Operation(String symbol) {
	     this.symbol = symbol;
	 
	}
	public String getSymbol() {		 
		switch(this){	
			case LIKE:
				return "like";
			case LIKESTART:
				return "like";
			case LIKEEND:
				return "like";
			case ILIKE:
				return "like";
			case ILIKESTART:
				return "like";
			case ILIKEEND:
				return "like";
			default:
				break;
		}
	    return  this.symbol;
	}
	
	public String toString() {		 
	     return  this.symbol;
	}
	
//	public static Operation getWhereOperationBySymbol(String symbol){
//		for (Operation whereOp : Operation.values()) {
//			if(whereOp.getSymbol().equalsIgnoreCase(symbol)){
//				return whereOp;
//			}
//		}
//		return null;
//	}
	public static Operation getOperation(String symbolOrName){
		for (Operation whereOp : Operation.values()) {
			//if(whereOp.name().equalsIgnoreCase(symbol)){
			if(whereOp.symbol.equalsIgnoreCase(symbolOrName) || whereOp.name().equalsIgnoreCase(symbolOrName)){
				return whereOp;
			}
		}
		return null;
	}



}
