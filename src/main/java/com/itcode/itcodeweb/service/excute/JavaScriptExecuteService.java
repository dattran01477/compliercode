package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.app.TestCase;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.data.TemplateService;

@Service
public class JavaScriptExecuteService extends AbstractExecuteService {

	@Autowired
	TemplateService templateService;

	@Override
	public void prepare(CodeSubmit code) {
		super.prepare(code);
	}

	@Override
	protected String mergeCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit) {
		StringBuilder code = new StringBuilder();
		code.append(template.getAssertFunction());
		code.append(codeSubmit.getCodeSubmit().getCode());
		for (TestCase test : codeSubmit.getTestCase()) {
			code.append(String.format(template.getResultFunction(), test.getCode()));
		}
		return code.toString();
	}

	@Override
	protected CodeTemplate getTemplateModel(CodeSubmit codeSubmit) {
		return templateService.findTemplateByName("javascript");
	}

	@Override
	protected CodeResult processCodeResult(CodeResult codeResult, CodeSubmit codeSubmit) {
		CodeResult codeResultAfterProcess = new CodeResult();
		codeResultAfterProcess.setInfoTestCase(codeSubmit.getTestCase());
		char[] arrayCharResult = codeResult.getResultCmd().toCharArray();

		if (codeResult.getError() == true) {
			codeResultAfterProcess.setResultCmd(codeResult.getResultCmd());
		} else {
			for (int i = 0; i < arrayCharResult.length; i++) {
				if (arrayCharResult[i] == '1') {
					codeResultAfterProcess.getInfoTestCase().get(i).setIsPassed(true);
				} else {
					codeResultAfterProcess.getInfoTestCase().get(i).setIsPassed(false);
				}
			}
		}

		return codeResultAfterProcess;
	}
}
