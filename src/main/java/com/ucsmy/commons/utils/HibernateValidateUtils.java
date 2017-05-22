package com.ucsmy.commons.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class HibernateValidateUtils {
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static String getErrors(Object obj) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);// 验证某个对象,，其实也可以只验证其中的某一个属性的

		Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
		while (iter.hasNext()) {
			return iter.next().getMessage();
		}
		return null;
	}

	public static String getErrors(Object... objs) {
		for(Object obj : objs) {
			String msg = getErrors(obj);
			if(msg != null || "".equals(msg))
				return msg;
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getErrors(new UserAccount(), new UserProfile()));
//	}
}
