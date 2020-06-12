package com.itcode.itcodeweb.model.respone;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeResult {
	private Double duration;

	private int exitCode;

	private ErrorMessage errorMessage = new ErrorMessage();

	private SuccessMessage successMessage = new SuccessMessage();
	
	private String stdout;
	
	private String sterr;
}
