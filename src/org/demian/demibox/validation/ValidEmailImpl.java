package org.demian.demibox.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailImpl implements ConstraintValidator<ValidEmail, String> {
	private EmailValidator validator;

	public void initialize(ValidEmail constraintAnnotation) {
		validator = EmailValidator.getInstance(false);
	}

	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (validator.isValid(email))
			return true;
		else
			return false;
	}
}