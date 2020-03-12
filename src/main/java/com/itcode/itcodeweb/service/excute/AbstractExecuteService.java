package com.itcode.itcodeweb.service.excute;

import java.util.Random;

import org.apache.logging.log4j.util.Strings;

import com.itcode.itcodeweb.data.ComplierEnum;
import com.itcode.itcodeweb.docker.DockerSandboxService;
import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.app.CodeTemplateModel;
import com.itcode.itcodeweb.model.docker.DockerSandboxModel;
import com.itcode.itcodeweb.model.respone.CodeResult;

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

	public void prepare(CodeSubmit code) {
		this.setComplierLanguage(code.getCodeSubmit().getLanguage());
		this.setCode(code);
		initService(code);
	}

	public void initService(CodeSubmit codeSubmit) {
		Random rand = new Random();
		String folder = FOLDER_BASE_PATH + rand.nextInt(10);
		String path = BASE_PATH;
		String vmName = VM_NAME;
		Long timeoutValue = TIMEOUT_VALUE;

		String code = buildCode();
		dockerSandBoxModel = new DockerSandboxModel(timeoutValue, path, folder, vmName,
				complierLanguage.getComplierName(), complierLanguage.getFileName(), code,
				complierLanguage.getOutputCommand(), complierLanguage.getLanguageName(),
				complierLanguage.getEArguments(), "");
	}

	@Override
	public CodeResult runComplier(DockerSandboxService dockerService) {
		dockerService.run(this.dockerSandBoxModel);
		return null;
	}

	public String buildCode() {
		CodeTemplateModel template = getTemplateModel(this.code);
		String code = mergeCodeWithTemplate(template, this.code);

		if (!Strings.isEmpty(code)) {
			return code;
		}

		new Throwable("Can't build code");
		return null;
	}

	protected abstract String mergeCodeWithTemplate(CodeTemplateModel template, CodeSubmit codeSubmit);

	protected abstract CodeTemplateModel getTemplateModel(CodeSubmit codeSubmit);
}
