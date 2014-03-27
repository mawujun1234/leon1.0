package com.mawujun.repository;

public class MyClass<T> {
	protected Class<T> entityClass;
	MyClass(Class<T> t) {
	    // ...
		this.entityClass=t;
	}
	public T get( T t) {
		return t;
	}
	public String get( String t) {
		return t;
	}
	
	public String get(){
		return "";
	}

}
