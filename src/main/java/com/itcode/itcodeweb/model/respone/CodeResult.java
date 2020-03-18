package com.itcode.itcodeweb.model.respone;

import java.util.List;

import com.itcode.itcodeweb.data.CodeResultStatus;
import com.itcode.itcodeweb.model.app.TestCase;

import lombok.Getter;
import lombok.Setter;

public class CodeResult {

	@Setter
	@Getter
	private Boolean error;

	@Getter
	@Setter
	private List<TestCase> infoTestCase;

	@Getter
	@Setter
	private String resultCmd;

	@Setter
	private CodeResultStatus status;

	public CodeResultStatus getStatus() {
		Boolean isSucess = true;
		if (infoTestCase != null & !infoTestCase.isEmpty()) {
			for (TestCase test : infoTestCase) {
				if (test.getIsPassed() == false) {
					isSucess = false;
				}
			}
		} else {
			isSucess = false;
		}

		if (isSucess) {
			return CodeResultStatus.SUCCESS;
		}

		return CodeResultStatus.FAIL;
	}
}
