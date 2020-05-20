package com.itcode.itcodeweb.model.app;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CodeSubmit {
	private Code codeSubmit;
	
	private List<TestCase> testCase;
}
