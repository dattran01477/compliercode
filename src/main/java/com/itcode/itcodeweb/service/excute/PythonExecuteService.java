package com.itcode.itcodeweb.service.excute;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;
import com.itcode.itcodeweb.model.app.TestCase;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;

@Service
public class PythonExecuteService extends AbstractExecuteCodeAndTestService {
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
		if (codeResult.getErrorMessage().getErrorComplieMessage().endsWith("OK")) {
			codeResult.getSuccessMessage()
					.setSuccessComplieMessage(codeResult.getErrorMessage().getErrorComplieMessage());
			codeResult.getErrorMessage().setErrorComplieMessage(null);
		}

		// Process output
		if(codeResult.getErrorMessage().getErrorComplieMessage()!=null) {
			String regexCheckFailed = "(?<=\\s\\{\\{\\%)(.*?)(?=\\%\\}\\})";
			StringBuilder testCaseResultFailed = new StringBuilder(
					codeResult.getErrorMessage().getErrorComplieMessage().toString());
			Pattern patternCheckTestCaseFailed = Pattern.compile(regexCheckFailed);
			Matcher matcherCheckTestCaseFailed = patternCheckTestCaseFailed.matcher(testCaseResultFailed);
			while (matcherCheckTestCaseFailed.find()) {
				codeResult.getTestCasesResult().add(new TestCase("", matcherCheckTestCaseFailed.group(1), false));
			}
		}
		

		if(codeResult.getSuccessMessage().getSuccessComplieMessage()!=null) {
			String regexCheckPassed = "(?<=\\{\\{)(.*?)(?=\\}\\})";
			StringBuilder testCaseResultPassed = new StringBuilder(
					codeResult.getSuccessMessage().getSuccessComplieMessage().toString());
			Pattern patternCheckTestCasePassed = Pattern.compile(regexCheckPassed);
			Matcher matcherCheckTestCasePassed = patternCheckTestCasePassed.matcher(testCaseResultPassed);
			while (matcherCheckTestCasePassed.find()) {
				codeResult.getTestCasesResult().add(new TestCase("", matcherCheckTestCasePassed.group(1), true));
			}
		}
		

		return codeResult;
	}
	

	@Override
	protected String mergeTestCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeSubmit) {
		return null;
	}
}
