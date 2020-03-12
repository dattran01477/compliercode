package com.itcode.itcodeweb.service.excute;

import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.app.CodeTemplateModel;

@Service
public class JavaExecuteService extends AbstractExecuteService {

	private CodeTemplateModel template;

	@Override
	public void prepare(CodeSubmit code) {
		buildTemplate();
		super.prepare(code);
	}

	private void buildTemplate() {
		this.template = CodeTemplateModel.builder().id(0l).assertFunction("").assertFunction("").build();
	}

	@Override
	protected String mergeCodeWithTemplate(CodeTemplateModel template, CodeSubmit codeSubmit) {
		return null;
	}

	@Override
	protected CodeTemplateModel getTemplateModel(CodeSubmit codeSubmit) {
		// TODO Auto-generated method stub
		return template;
	}

}
