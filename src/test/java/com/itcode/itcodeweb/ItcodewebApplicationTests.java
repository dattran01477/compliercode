package com.itcode.itcodeweb;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.docker.DockerSandboxModel;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;

@SpringBootTest
class ItcodewebApplicationTests {
	@Autowired
	DockerSandboxService dockerService;

	@Test
	void contextLoads() {

		Random rand = new Random();
		String folder = "temp/" + rand.nextInt(10);
		String path = System.getProperty("user.dir") + "/";
		String vmName = "virtual_machine";
		Long timeoutValue = 20l;

		String code = "public class files{\n" + "    public static void main(String[] args) {\n"
				+ "        System.out.println(\"Hello World!\");\n" + "    }\n" + "}";
		String stdinData = "Hello World!";

		ComplierEnum complierForLanguage = ComplierEnum.valueOf("Java");

		DockerSandboxModel model = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierForLanguage.getComplierName(), complierForLanguage.getFileName(), code,
				complierForLanguage.getOutputCommand(), complierForLanguage.getLanguageName(),
				complierForLanguage.getEArguments(), stdinData);
		dockerService.run(model);
	}

	@Test
	void testComplierJavaScript() throws InterruptedException {
		Random rand = new Random();
		String folder = "temp/" + rand.nextInt(10);
		String path = System.getProperty("user.dir") + "/";
		String vmName = "virtual_machine";
		Long timeoutValue = 20l;

		String code = "console.log(\"hello\")";
		String stdinData = "hello";

		ComplierEnum complierForLanguage = ComplierEnum.valueOf("Nodejs");

		DockerSandboxModel model = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierForLanguage.getComplierName(), complierForLanguage.getFileName(), code,
				complierForLanguage.getOutputCommand(), complierForLanguage.getLanguageName(),
				complierForLanguage.getEArguments(), stdinData);
		dockerService.run(model);

		
	}

}
