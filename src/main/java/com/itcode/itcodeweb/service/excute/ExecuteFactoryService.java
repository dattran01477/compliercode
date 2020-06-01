package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;

@Service
public class ExecuteFactoryService {

	@Autowired
	JavaScriptExecuteService javascriptExecuteService;

	@Autowired
	JavaExecuteService javaExecuteService;

	private ExecuteFactoryService() {

	}

	public final IExecuteService<CodeAndTestCaseSubmit> getExcute(ComplierEnum complierType) {
		switch (complierType) {
		case Java:
			return javaExecuteService;
		case JavaTest:
			return javaExecuteService;
		case Nodejs:
			return javascriptExecuteService;
		case NodejsTest:
			return javascriptExecuteService;
		default:
			throw new IllegalArgumentException("Not support language");
		}
	}
}
