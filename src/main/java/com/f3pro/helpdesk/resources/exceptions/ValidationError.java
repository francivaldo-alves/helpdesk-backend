package com.f3pro.helpdesk.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError  extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessege> erros = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Long timestamp, Integer status, String error, String messege, String path) {
		super(timestamp, status, error, messege, path);
	}

	public List<FieldMessege> getErros() {
		return erros;
	}

	public void addError(String fieldName, String message) {
		this.erros.add(new FieldMessege(fieldName, message));
	}
	
	

}
