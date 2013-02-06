package com.mawujun.repository.hibernate.validate;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidateEntityTest {
	
	
	
	static class Inner_ValidateEntity extends ValidateEntity {
		@Email   
		@Size(max=32)     
		protected String email;

		@NotBlank
		protected String userName;

	}
	
	@Before	 
    public void setUp() throws Exception {
		//.getCommodity();
    }

	@Test(expected=ConstraintViolationException.class)
	public void validateEmail() {
		Inner_ValidateEntity validateEntity = new ValidateEntityTest.Inner_ValidateEntity();
		validateEntity.email="dfsdf";
		validateEntity.userName="11";
		validateEntity.validate();	
	}
	
	@Test
	public void validate() {
		Inner_ValidateEntity validateEntity = new ValidateEntityTest.Inner_ValidateEntity();
		validateEntity.email="1dfgdf@163.com";
		//validateEntity.userName="11";
		try {
			validateEntity.validate();	
		} catch(ConstraintViolationException e){
			Assert.assertEquals(1, e.getConstraintViolations().size());
			Assert.assertNull( e.getConstraintViolations().iterator().next().getInvalidValue());
			
		}
		
		validateEntity.userName="";
		try {
			validateEntity.validate();	
		} catch(ConstraintViolationException e){
			Assert.assertEquals(1, e.getConstraintViolations().size());
			Assert.assertEquals("", e.getConstraintViolations().iterator().next().getInvalidValue());
			
		}
		
		validateEntity.email="1dfgdf";
		validateEntity.userName="";
		try {
			validateEntity.validate(";");	//<ConstraintViolation<Inner_AutoIdEntityValidate>>
		} catch(ValidationException e){
			//System.out.println(e.getMessage());
			Assert.assertEquals(2, e.getMessage().split(";").length);

		}
	}

	

}
