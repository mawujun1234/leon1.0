package com.mawujun.controller.spring.mvc;

import com.mawujun.utils.page.QueryResult;


public class JsonResult {
	
	private Boolean success=true;
	private String message;
	private  Object root;//即可用于AJAX的返回时，带回去的对象，也可用于grid等返回的对象数组
	private  Object errors;//当发生错误的时候，要带回去的额外信息
	
	private int totalProperty;
	
	public static JsonResult initResult(){
		return new JsonResult();
	}
	
	public static JsonResult initResult(Object root){
		if(root instanceof QueryResult){
			return JsonResult.initResult((QueryResult)root);
		}
		JsonResult aa= new JsonResult(root);
		aa.setTotalProperty(1);
		return aa;
	}
	public static JsonResult initResult(QueryResult queryResult){
		JsonResult aa= new JsonResult();
		aa.setQueryResult(queryResult);
		return aa;
	}
	
	public static JsonResult initErrorResult(){
		JsonResult error=  new JsonResult();
		error.setSuccess(false);
		return error;
	}
	public static JsonResult initErrorResult(Object root){
		JsonResult error=  new JsonResult();
		error.setSuccess(false);
		error.setErrors(root);
		return error;
	}
	public static JsonResult initErrorResult(String message){
		JsonResult error=  new JsonResult();
		error.setSuccess(false);
		error.setMessage(message);
		return error;
	}
	public static JsonResult initErrorResult(String message,Object root){
		JsonResult error=  new JsonResult();
		error.setSuccess(false);
		error.setErrors(root);
		error.setMessage(message);
		return error;
	}
	
	public JsonResult() {
		super();
	}
	public JsonResult(Object root) {
		super();
		//this.root = root;
//		if(!root.getClass().isArray()){
//			this.setRoot(new Object[]{root});
//		} else {
			this.setRoot(root);
//		}
		
	}


	public Boolean getSuccess() {
		return success;
	}

	private void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}
	public void setQueryResult(QueryResult queryResult) {
		this.totalProperty=queryResult.getTotalItems();
		this.root=queryResult.getResult();
	}

	public int getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}
	
}
