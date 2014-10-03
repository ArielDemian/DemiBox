package org.demian.demibox.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = org.demian.demibox.validation.ValidEmailImpl.class)
public @interface ValidEmail {
	String message() default "This does not appear to be a valid email.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}