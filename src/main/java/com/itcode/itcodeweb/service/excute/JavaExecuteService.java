package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.data.TemplateService;

@Service
public class JavaExecuteService extends AbstractExecuteService {

	@Autowired
	TemplateService templateService;

	@Override
	public void prepare(CodeSubmit code) {
		super.prepare(code);
	}

	@Override
	protected String mergeCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit) {
		StringBuilder code = new StringBuilder();
//		code.append(template.getAssertFunction());
//		code.append(codeSubmit.getCodeSubmit().getCode());
//		for (TestCase test : codeSubmit.getTestCase()) {
//			code.append(String.format(template.getResultFunction(), test.getCode()));
//		}
		return code.toString();
	}

	@Override
	protected CodeTemplate getTemplateCodeModel(CodeSubmit codeSubmit) {
		return templateService.findTemplateByName("java");
	}

	@Override
	protected CodeResult processCodeResult(CodeResult codeResult, CodeSubmit codeSubmit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String mergeTestCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit) {
		if (!StringUtils.isEmpty(codeSubmit)) {
			new Throwable("code empty!");
		}

		return codeSubmit.getCodeSubmit().getCode();
	}

	@Override
	protected CodeTemplate getTemplateTestCodeModel(CodeSubmit codeSubmit) {
		return templateService.findTemplateByName("java-test");
	}

}
