package com.itcode.itcodeweb.service.execute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.Code;
import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;
import com.itcode.itcodeweb.service.excute.ExecuteFactoryService;
import com.itcode.itcodeweb.service.excute.IExecuteService;
import com.itcode.itcodeweb.service.excute.JavaScriptExecuteService;

@SpringBootTest
public class JavaScriptExecuteServiceTest {
	

	@Autowired
	JavaScriptExecuteService javaScriptExecuteService;

	private static final String codeSubmit = "var assert = require('assert');\n"
			+ "const AssertionError = require('assert').AssertionError;\n" + "function convertToF(celsius) {\n"
			+ "    let fahrenheit = celsius * 9 / 5 + 32;\n" + "    return \"fahrenheit\";\n" + "}\n" + "try {\n"
			+ "    assert.strictEqual(typeof convertToF(0) === 'number',true, new AssertionError({expected: \"convertToF(0)</code> should return a number\"}));\n"
			+ "} catch (err) {\n" + "    console.log(JSON.stringify(err));\n" + "}";

	@Autowired
	ExecuteFactoryService executeFactoryService;

	private CodeAndTestCaseSubmit codeAndTestSubmit;

	@BeforeEach
	public void beforTest() {
		ComplierEnum complierEnumForLanguage = ComplierEnum.NodejsTest;
		Code code = new Code(null, false, codeSubmit);
		codeAndTestSubmit = new CodeAndTestCaseSubmit(code, complierEnumForLanguage, null);
	}

	@Test
	public void testBuildCodeJavaScript() {
		ComplierEnum complierEnumForLanguage = codeAndTestSubmit.getLanguage();
		IExecuteService<CodeAndTestCaseSubmit> complier = executeFactoryService.getExcute(complierEnumForLanguage);
		DockerSandboxService service = new DockerSandboxService();
		CodeResult result = complier.runComplier(service, codeAndTestSubmit);
	}
}
