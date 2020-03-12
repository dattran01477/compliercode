package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.app.CodeTemplateModel;
import com.itcode.itcodeweb.model.app.TestCase;
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
	protected String mergeCodeWithTemplate(CodeTemplateModel template, CodeSubmit codeSubmit) {
		StringBuilder code = new StringBuilder();
		code.append(template.getAssertFunction());
		code.append(codeSubmit.getCodeSubmit().getCode());
		for (TestCase test : codeSubmit.getTestCase()) {
			code.append(String.format(template.getResultFunction(), test.getCode()));
		}
		return code.toString();
	}

	@Override
	protected CodeTemplateModel getTemplateModel(CodeSubmit codeSubmit) {
		return null;
	}
}
