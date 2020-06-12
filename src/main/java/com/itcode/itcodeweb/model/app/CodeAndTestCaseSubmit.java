package com.itcode.itcodeweb.model.app;

import com.itcode.itcodeweb.data.ComplierEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeAndTestCaseSubmit extends CodeSubmit {
	public CodeAndTestCaseSubmit(Code code, ComplierEnum language, Code test, String languageVersion) {
		super(code, language, languageVersion);
		this.test = test;
	}

	private Code test;
}
