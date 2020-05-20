package com.itcode.itcodeweb.service.excute;

import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.docker.DockerSandboxModel;
import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractExecuteService implements IExecuteService {

	private static final String FOLDER_BASE_PATH = "temp/";

	private static final String BASE_PATH = System.getProperty("user.dir") + "/";

	private static final String VM_NAME = "virtual_machine";

	private static final Long TIMEOUT_VALUE = 20l;

	private ComplierEnum complierLanguage;

	private DockerSandboxModel dockerSandBoxModel;

	private CodeSubmit code;

	@Autowired
	private Environment env;

	public void prepare(CodeSubmit code) {
		this.setComplierLanguage(code.getCodeSubmit().getLanguage());
		this.setCode(code);
		initService();
	}

	public void initService() {
		Random rand = new Random();
		String folder = FOLDER_BASE_PATH + rand.nextInt(10);
		String path = "";
		if (StringUtils.isEmpty(env.getProperty("itcode.home"))) {
			path = BASE_PATH;
		} else {
			path = env.getProperty("itcode.home");
		}
		String vmName = VM_NAME;
		Long timeoutValue = TIMEOUT_VALUE;

		String code = buildCode();
		String testCode = "";
		dockerSandBoxModel = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierLanguage.getComplierName(), complierLanguage.getFileName(), code,
				complierLanguage.getOutputCommand(), complierLanguage.getLanguageName(),
				complierLanguage.getEArguments(), "", complierLanguage.getTestFileName(), testCode);
	}

	@Override
	public CodeResult runComplier(DockerSandboxService dockerService, CodeSubmit code) {
		prepare(code);
		CodeResult codeResult = dockerService.run(this.dockerSandBoxModel);
		return processCodeResult(codeResult, this.code);
	}

	public String buildCode() {
		CodeTemplate template = getTemplateCodeModel(this.code);
		String code = mergeCodeWithTemplate(template, this.code);

		if (!Strings.isEmpty(code)) {
			return code;
		}

		new Throwable("Can't build code");
		return "";
	}

	public String buildTestCode() {
		CodeTemplate template = getTemplateTestCodeModel(this.code);
		String code = mergeTestCodeWithTemplate(template, this.code);

		if (!Strings.isEmpty(code)) {
			return code;
		}

		new Throwable("Can't build test code");
		return "";
	}

	protected abstract CodeResult processCodeResult(CodeResult codeResult, CodeSubmit codeSubmit);

	protected abstract String mergeCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit);

	protected abstract String mergeTestCodeWithTemplate(CodeTemplate template, CodeSubmit codeSubmit);

	protected abstract CodeTemplate getTemplateCodeModel(CodeSubmit codeSubmit);

	protected abstract CodeTemplate getTemplateTestCodeModel(CodeSubmit codeSubmit);
}
