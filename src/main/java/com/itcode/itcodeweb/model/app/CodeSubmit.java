package com.itcode.itcodeweb.model.app;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeSubmit {
	private Code codeSubmit;
	
	private List<TestCase> testCase;
}
