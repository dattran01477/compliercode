package com.itcode.itcodeweb.service.excute;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;

@Service
public class JavaExecuteService extends AbstractExecuteCodeAndTestService {

	@Override
	public void prepare(CodeAndTestCaseSubmit code) {
		super.prepare(code);
	}

	@Override
	protected String mergeCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeSubmit) {
		if (!StringUtils.isEmpty(codeSubmit.getCodeSubmit().getCode())) {
			// Should validation code after return
			return codeSubmit.getCodeSubmit().getCode();
		}

		return "";
	}

	@Override
	protected CodeResult processCodeResult(CodeResult codeResult, CodeAndTestCaseSubmit codeSubmit) {
		return codeResult;
	}

	@Override
	protected String mergeTestCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeTestSubmit) {
		if (!StringUtils.isEmpty(codeTestSubmit.getTest().getCode())) {
			// Should validation test code after return
			return codeTestSubmit.getTest().getCode();
		}
		return "";
	}
}
