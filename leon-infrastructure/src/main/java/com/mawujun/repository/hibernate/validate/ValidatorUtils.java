package com.mawujun.repository.hibernate.validate;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.common.collect.Lists;
import com.mawujun.utils.StringUtils;

public class ValidatorUtils {
	 //private final Log logger = LogFactory.getLog(getClass());

	private static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	/**
	 * 会抛出ConstraintViolationException异常，它包含一个Set<ConstraintViolation<T>>的异常集合
	 * @param t
	 * @exception ConstraintViolationException
	 */
	public static <T> void validate(T t) { 
		 Set<ConstraintViolation<T>> constraintViolations = validator.validate(t); 
		 if(constraintViolations.size() > 0) { 
			 throw new ConstraintViolationException(constraintViolations);
//			 String validateError = ""; 
//			 for(ConstraintViolation<T> constraintViolation: constraintViolations) { 
//				 validateError += constraintViolation.getMessage() + ";"; 
//			 }
//			 throw new ValidateException(validateError); 
		 }
	}
	/**
	 * 如果验证异常，将会派出ValidationException异常，异常信息以字符串的形式返回，分隔符默认是分号，也可以自己指定
	 * @param separator
	 * @exception ValidationException
	 */
	public static <T> void validateAndReturnMessage(T t, String... separator) { 
		String errorMsg=null;
		try{
			ValidatorUtils.validate(t);
		} catch(ConstraintViolationException e){
			String sep=";";
			if(separator!=null && separator.length>0){
				sep=separator[0];
			}
			errorMsg=ValidatorUtils.convertMessage(e, sep);
			
		}
		throw new ValidationException(errorMsg); 
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为字符串, 以separator分割.
	 */
	public static String convertMessage(Set<? extends ConstraintViolation> constraintViolations, String separator) {
		List<String> errorMessages = Lists.newArrayList();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getMessage());
		}
		return StringUtils.join(errorMessages, separator);
	}

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为字符串, 以separator分割.
	 */
	public static String convertMessage(ConstraintViolationException e, String separator) {
		return convertMessage(e.getConstraintViolations(), separator);
	}

}
