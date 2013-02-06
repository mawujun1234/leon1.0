package com.mawujun.repository.idEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;


/**
 * 添加了验证的方法
 * @author mawujun
 *
 * @param <ID> 这个参数是指定什么类型的id，是int还是long
 */
@MappedSuperclass
public abstract class AutoIdEntityValidate<ID> extends AutoIdEntity<ID> {
	
}
