package com.mawujun.repository.hibernate.validate;

import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@MappedSuperclass
public class ValidateEntity {
	/**
	 * 会抛出ConstraintViolationException异常
	 * @exception ConstraintViolationException
	 */
	public void validate(){
		ValidatorUtils.validate(this);
	}
	/**
	 * 将错误信息返回以separator分隔符进行分割，默认分隔符是；,并放在了ValidationException异常里面
	 * @param separator
	 * @exception ValidationException
	 */
	public void validate(String... separator){	
		ValidatorUtils.validateAndReturnMessage(this);
	}

}
