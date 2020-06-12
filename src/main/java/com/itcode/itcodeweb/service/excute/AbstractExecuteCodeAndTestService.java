package com.itcode.itcodeweb.service.excute;

import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.CodeAndTestCaseSubmit;
import com.itcode.itcodeweb.model.docker.DockerSandboxModel;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractExecuteCodeAndTestService implements IExecuteService<CodeAndTestCaseSubmit> {

	private static final String FOLDER_BASE_PATH = "temp/";

	private static final String BASE_PATH = System.getProperty("user.dir") + "/";

	private static final String VM_NAME = "virtual.name";

	private static final Long TIMEOUT_VALUE = 20l;

	private static final String APP_HOME = "itcode.home";

	private ComplierEnum complierLanguage;

	private DockerSandboxModel dockerSandBoxModel;

	private CodeAndTestCaseSubmit code;

	@Autowired
	private Environment env;

	public void prepare(CodeAndTestCaseSubmit code) {
		this.setComplierLanguage(code.getLanguage());
		this.setCode(code);
		initService();
	}

	public void initService() {
		Random rand = new Random();
		String folder = FOLDER_BASE_PATH + rand.nextInt(10);
		String path = "";
		if (StringUtils.isEmpty(env.getProperty(APP_HOME))) {
			path = BASE_PATH;
		} else {
			path = env.getProperty(APP_HOME);
		}
		
		String vmName =env.getProperty(VM_NAME)!= null?env.getProperty(VM_NAME):"" ;
		Long timeoutValue = TIMEOUT_VALUE;

		String code = buildCode();
		String testCode = buildTestCode();
		dockerSandBoxModel = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierLanguage.getComplierName(), complierLanguage.getFileName(), code,
				complierLanguage.getOutputCommand(), complierLanguage.getLanguageName(),
				complierLanguage.getEArguments(), "", complierLanguage.getTestFileName(), testCode);
	}

	@Override
	public CodeResult runComplier(DockerSandboxService dockerService, CodeAndTestCaseSubmit code) {
		prepare(code);
		CodeResult codeResult = dockerService.run(this.dockerSandBoxModel);
		return processCodeResult(codeResult, this.code);
	}

	public String buildCode() {
		String code = mergeCodeWithTemplate(null, this.code);

		if (!Strings.isEmpty(code)) {
			return code;
		}

		new Throwable("Can't build code");
		return "";
	}

	public String buildTestCode() {
		String code = mergeTestCodeWithTemplate(null, this.code);

		if (!Strings.isEmpty(code)) {
			return code;
		}

		new Throwable("Can't build test code");
		return "";
	}

	protected abstract CodeResult processCodeResult(CodeResult codeResult, CodeAndTestCaseSubmit codeSubmit);

	protected abstract String mergeCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeSubmit);

	protected abstract String mergeTestCodeWithTemplate(CodeTemplate template, CodeAndTestCaseSubmit codeSubmit);
}
