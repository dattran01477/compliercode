package com.itcode.itcodeweb.service.excute;

import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;

@Service
public class JavaExecuteService extends AbstractExecuteService {

	@Override
	public void prepare(CodeSubmit code) {
		super.prepare(code);
	}

	@Override
	protected String mergeCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit) {
		return null;
	}

	@Override
	protected CodeTemplate getTemplateModel(CodeSubmit codeSubmit) {
		return null;
	}

	@Override
	protected CodeResult processCodeResult(CodeResult codeResult, CodeSubmit codeSubmit) {
		// TODO Auto-generated method stub
		return null;
	}

}
