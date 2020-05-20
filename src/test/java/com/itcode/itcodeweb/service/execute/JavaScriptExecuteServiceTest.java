package com.itcode.itcodeweb.service.execute;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.Code;
import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.app.TestCase;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;
import com.itcode.itcodeweb.service.excute.JavaScriptExecuteService;

@SpringBootTest
public class JavaScriptExecuteServiceTest {

	@Autowired
	JavaScriptExecuteService javaScriptExecuteService;

	private static final String codeSubmit = "function convertToF(celsius) { var fahrenheit = celsius * 9/5 + 32;   return fahrenheit;}";

	private CodeSubmit codeSubmitForm;

	@BeforeEach
	public void beforTest() {
		CodeSubmit codeSubmitFormTmp = new CodeSubmit(null, null);
		List<TestCase> lsTestCase = new ArrayList<TestCase>();
		TestCase test1 = new TestCase("1", "assert(typeof convertToF(0) === 'number')", false);
		TestCase test2 = new TestCase("1", "assert(convertToF(-30) === -22)", false);
		TestCase test3 = new TestCase("1", "assert(convertToF(-10) === 14)", false);
		TestCase test4 = new TestCase("1", "assert(convertToF(0) === 32)", false);
		TestCase test5 = new TestCase("1", "assert(convertToF(20) === 68)", false);

		lsTestCase.add(test1);
		lsTestCase.add(test2);
		lsTestCase.add(test3);
		lsTestCase.add(test4);
		lsTestCase.add(test5);

		codeSubmitFormTmp.setTestCase(lsTestCase);

		Code code = new Code(ComplierEnum.valueOf("Nodejs"), false, codeSubmit);
		codeSubmitFormTmp.setCodeSubmit(code);
		codeSubmitForm = codeSubmitFormTmp;
	}

	@Test
	public void testBuildCodeJavaScript() {
		DockerSandboxService docker = new DockerSandboxService();
		javaScriptExecuteService.prepare(this.codeSubmitForm);
		javaScriptExecuteService.runComplier(docker, this.codeSubmitForm);
	}
}
