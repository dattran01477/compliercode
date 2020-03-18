package com.itcode.itcodeweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;
import com.itcode.itcodeweb.service.excute.ExecuteFactoryService;
import com.itcode.itcodeweb.service.excute.IExecuteService;

@RestController
@RequestMapping("/code")
public class TestCaseController {

	@Autowired
	ExecuteFactoryService complierFactory;

	@PostMapping("/{language}")
	public CodeResult runTest(@PathVariable(value = "language") String language, @RequestBody CodeSubmit codeSubmit) {
		ComplierEnum complierEnumForLanguage = ComplierEnum.valueOf(language);
		IExecuteService complier = complierFactory.getExcute(complierEnumForLanguage);
		DockerSandboxService service = new DockerSandboxService();
		CodeResult result = complier.runComplier(service, codeSubmit);
		return result;
	}

}
