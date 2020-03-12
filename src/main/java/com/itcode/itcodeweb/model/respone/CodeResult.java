package com.itcode.itcodeweb.model.respone;

import java.util.List;

import com.itcode.itcodeweb.data.CodeResultStatus;
import com.itcode.itcodeweb.model.app.TestCase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeResult {
	
	private CodeResultStatus status;

	private List<TestCase> infoTestCase;
}
