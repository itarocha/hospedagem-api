package br.com.itarocha.hospedagem.exception;

import br.com.itarocha.hospedagem.validation.ResultError;

public class ValidationException extends Exception{

	private static final long serialVersionUID = -1173120804319840769L;

	private ResultError re;
	
	public ValidationException(ResultError re) {
		this.re = re;
	}

	public ResultError getRe() {
		return re;
	}

	public void setRe(ResultError re) {
		this.re = re;
	}
	
}
