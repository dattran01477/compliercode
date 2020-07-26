package com.itcode.itcodeweb.model.respone;

import java.util.ArrayList;
import java.util.List;

import com.itcode.itcodeweb.model.app.TestCase;

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
	
	private List<TestCase> testCasesResult=new ArrayList<TestCase>();
}
