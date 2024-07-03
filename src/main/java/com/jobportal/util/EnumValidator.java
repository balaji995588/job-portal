package com.jobportal.util;

import org.springframework.stereotype.Component;

import com.jobportal.annotation.ValidEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
	private Class<? extends Enum<?>> enumClass;

	@Override
	public void initialize(ValidEnum constraintAnnotation) {
		enumClass = constraintAnnotation.enumClass();
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			if (enumValue.name().equals(value.name())) {
				return true;
			}
		}

		return false;
	}
}
