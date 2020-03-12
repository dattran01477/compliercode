package com.itcode.itcodeweb.docker;

import java.util.Random;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.docker.DockerSandboxModel;

public class testmain {

	public static void main(String[] args) {
		Random rand = new Random();
		String folder = "temp/" + rand.nextInt(10);
		String path = System.getProperty("user.dir") + "/";
		String vmName = "virtual_machine";
		Long timeoutValue = 20l;

		String code = "public class HelloWorld {\n" + "    public static void main(String[] args) {\n"
				+ "        System.out.println(\"Hello World!\");\n" + "    }\n" + "}";
		String stdinData = "Hello World!";

		ComplierEnum complierForLanguage = ComplierEnum.valueOf("Java");

		DockerSandboxModel model = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierForLanguage.getComplierName(), complierForLanguage.getFileName(), code,
				complierForLanguage.getOutputCommand(), complierForLanguage.getLanguageName(),
				complierForLanguage.getEArguments(), stdinData);
		DockerSandboxService dockerService = new DockerSandboxService();
		dockerService.run(model);
	}

}
