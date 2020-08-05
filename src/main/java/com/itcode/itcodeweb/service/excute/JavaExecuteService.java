package com.itcode.itcodeweb.service.excute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;
import com.itcode.itcodeweb.model.app.TestCase;
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
		// Process output
		Map<String, TestCase> mapResult = new HashMap<>();

		String regex = "(?<=\\{\\{)(.*?[^?])(?=\\}\\})";
		StringBuilder testCaseResult = new StringBuilder(
				codeResult.getSuccessMessage().getSuccessComplieMessage().toString());
		Pattern patternAllTestCase = Pattern.compile(regex);
		Matcher matcherAllTestCase = patternAllTestCase.matcher(testCaseResult);
		while (matcherAllTestCase.find()) {
			mapResult.put(matcherAllTestCase.group(1), new TestCase("", matcherAllTestCase.group(1), false));
		}

		String regexCheckPassed = "(?<=\\{\\{\\?)(.*?)(?=\\?\\}\\})";
		StringBuilder testCaseResultPassed = new StringBuilder(
				codeResult.getSuccessMessage().getSuccessComplieMessage().toString());
		Pattern patternCheckTestCasePassed = Pattern.compile(regexCheckPassed);
		Matcher matcherCheckTestCasePassed = patternCheckTestCasePassed.matcher(testCaseResultPassed);
		while (matcherCheckTestCasePassed.find()) {
			mapResult.put(matcherCheckTestCasePassed.group(1), new TestCase("", matcherCheckTestCasePassed.group(1), true));
		}

		for (Map.Entry<String, TestCase> entry : mapResult.entrySet()) {
			codeResult.getTestCasesResult().add(entry.getValue());
		}

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
