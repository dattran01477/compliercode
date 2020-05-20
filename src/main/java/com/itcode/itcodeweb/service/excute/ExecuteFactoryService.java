package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.data.ComplierEnum;

@Service
public class ExecuteFactoryService {

	@Autowired
	JavaScriptExecuteService javascriptExecuteService;

	@Autowired
	JavaExecuteService javaExecuteService;

	private ExecuteFactoryService() {

	}

	public final IExecuteService getExcute(ComplierEnum complierType) {
		switch (complierType) {
		case Java:
			return javaExecuteService;
		case Nodejs:
			return javascriptExecuteService;
		default:
			throw new IllegalArgumentException("Not support language");
		}
	}
}
