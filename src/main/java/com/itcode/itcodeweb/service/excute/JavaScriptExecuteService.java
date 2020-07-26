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
public class JavaScriptExecuteService extends AbstractExecuteCodeAndTestService {

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
		String regex = "(?<=\\{\\{)(.*?)(?=\\}\\})";
		StringBuilder testCaseResult = new StringBuilder(
				codeResult.getSuccessMessage().getSuccessComplieMessage().toString());
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(testCaseResult.substring(0,
				testCaseResult.indexOf("failing") != -1 ? testCaseResult.indexOf("failing") : testCaseResult.length()));
		List<String> listMatches = new ArrayList<>();

		while (matcher.find()) {
			listMatches.add(matcher.group(1));
		}

		for (int i = 0; i < listMatches.size(); i++) {
			TestCase testCase = new TestCase();
			testCase.setCode(listMatches.get(i));
			if (testCaseResult.charAt(testCaseResult.indexOf(listMatches.get(i)) - 4) == '✓') {
				testCase.setIsPassed(true);
			} else {
				testCase.setIsPassed(false);
			}
			codeResult.getTestCasesResult().add(testCase);
		}

		return codeResult;
	}

	@Override
	protected String mergeTestCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeSubmit) {
		return null;
	}
}
