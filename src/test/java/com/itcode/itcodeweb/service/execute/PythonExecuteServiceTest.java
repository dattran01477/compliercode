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
public class PythonExecuteServiceTest {
	@Autowired
	JavaScriptExecuteService javaScriptExecuteService;

	private static final String codeSubmit = "# Python code to demonstrate working of unittest \nimport unittest \n    \nclass TestStringMethods(unittest.TestCase):\n      \"\"\"Sample test case\"\"\"\n      \n      # Setting up for the test\n      def setUp(self):\n          pass\n      \n      # Cleaning up after the test\n      def tearDown(self):\n          pass\n      \n      # Returns True if the string contains 6 a. \n      def test_strings_a(self): \n          self.assertEqual( 'a'*6, 'aaaaaa') \n      \n      # Returns True if the string is in upper case. \n      def test_upper(self):\n          self.assertEqual('love'.upper(), 'LOVE') \n      \n      # Returns True if the string is in uppercase \n      # else returns False. \n      def test_isupper(self):\n          self.assertTrue('LOVE'.isupper()) \n          self.assertFalse('Love'.isupper())  \n   \nif __name__ == '__main__': \n       unittest.main() ";

	@Autowired
	ExecuteFactoryService executeFactoryService;

	private CodeAndTestCaseSubmit codeAndTestSubmit;

	@BeforeEach
	public void beforTest() {
		ComplierEnum complierEnumForLanguage = ComplierEnum.Python2;
		Code code = new Code(null, false, codeSubmit);
		codeAndTestSubmit = new CodeAndTestCaseSubmit(code, complierEnumForLanguage, null,"");
	}

	@Test
	public void testBuildCodeJavaScript() {
		ComplierEnum complierEnumForLanguage = codeAndTestSubmit.getLanguage();
		IExecuteService<CodeAndTestCaseSubmit> complier = executeFactoryService.getExcute(complierEnumForLanguage);
		DockerSandboxService service = new DockerSandboxService();
		CodeResult result = complier.runComplier(service, codeAndTestSubmit);
	
	}
}
