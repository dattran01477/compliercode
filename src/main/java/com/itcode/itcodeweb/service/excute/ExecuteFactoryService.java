package com.itcode.itcodeweb.service.excute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.data.ComplierEnum;

@Service
public class ExecuteFactoryService {

	@Autowired
	JavaScriptExecuteService javascriptExecuteService;

	private ExecuteFactoryService() {

	}

	public static final IExecuteService getExcute(ComplierEnum complierType) {
		switch (complierType) {
		case Java:
			return new JavaExecuteService();
		case Nodejs:
			return new JavaScriptExecuteService();
		default:
			throw new IllegalArgumentException("Not support language");
		}
	}
}
