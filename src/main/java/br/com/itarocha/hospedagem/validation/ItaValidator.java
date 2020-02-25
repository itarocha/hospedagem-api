package br.com.itarocha.hospedagem.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ItaValidator<T> {

	private T ref;
	private ResultError re;

	public ItaValidator(T ref) {
		this.ref = ref;
		this.re = new ResultError();
	}

	public ItaValidator<T> validate(Validator validator) {
		this.re.getErrors().clear();
		
		//ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		//Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(this.ref);
		for (ConstraintViolation<T> violation : violations ) {
			this.re.addError(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return this;
	}

	public boolean hasErrors() {
		return !this.re.getErrors().isEmpty();
	}
	
	public ResultError getErrors() {
		return this.re;
	}

	public void addError(String fieldName, String message) {
		this.re.addError(fieldName, message);
	}
}
