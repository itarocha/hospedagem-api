package br.com.itarocha.hospedagem.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultError implements Serializable {
	
	private static final long serialVersionUID = 374333955639429515L;
	
	private List<FieldValidationError> errors;
	
	public ResultError() {
		this.errors = new ArrayList<>();
	}

	public List<FieldValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldValidationError> errors) {
		this.errors = errors;
	}
	
	public ResultError addError(String fieldName, String message) {
		this.errors.add(new FieldValidationError(fieldName, message));
		return this;
	}
}
