package com.itcode.itcodeweb.service.docker;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author thanhdat
 *
 */
@Slf4j
public class DockerSandboxService extends AbstractDockerSandBoxService {

	private static final String END_PROCESS_CHAR = "-COMPILEBOX::ENDOFOUTPUT-";

	@Autowired
	Environment env;

	@Override
	public void prepare() {
		try {
			// step 1
			File source = new File(this.dockerSandboxModel.getPath() + "Payload/");
			File destOne = new File(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
			FileUtils.coppyFolderAndAllowExcuteable(source, destOne);
			Runtime.getRuntime()
					.exec("chmod 777 " + this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
			Runtime.getRuntime()
					.exec("chmod +x " + this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/*");

			// step 2
			/* create file code */
			FileUtils.writeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/"
					+ this.dockerSandboxModel.getFileName(), this.dockerSandboxModel.getCode());
			/* Create file test code */
			if (!StringUtils.isEmpty(this.dockerSandboxModel.getTestCode())) {
				FileUtils.writeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/"
						+ this.dockerSandboxModel.getTestFileName(), this.dockerSandboxModel.getTestCode());
			}
			log.info(this.dockerSandboxModel.getLangName() + " End process file code and file test code!");
			Runtime.getRuntime().exec("chmod 777 \"" + this.dockerSandboxModel.getPath()
					+ this.dockerSandboxModel.getFolder() + "/" + this.dockerSandboxModel.getFileName() + "\"");

			// step 3
			FileUtils.writeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/inputFile",
					this.dockerSandboxModel.getStdinData());
			FileUtils.setExcuteableAllFileInFolder(destOne);
			log.info("Input file was saved!");
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	public CodeResult execute() {
		String st = this.dockerSandboxModel.getPath() + "DockerTimeout.sh " + this.dockerSandboxModel.getTimeOutValue()
				+ "s -u mysql -e \'NODE_PATH=/usr/local/lib/node_modules\' -i -t -v  "
				+ this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + ":/usercode "
				+ this.dockerSandboxModel.getVmName() + " /usercode/script.sh "
				+ this.dockerSandboxModel.getComplierName() + " " + this.dockerSandboxModel.getFileName() + " "
				+ this.dockerSandboxModel.getOutPutCommand() + " " + this.dockerSandboxModel.getExtraArguments();
		log.info(st);
		try {
			Runtime.getRuntime().exec(st).waitFor();
			return scanFileSuccess();
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
		}
		return new CodeResult();
	}

	public CodeResult scanFileSuccess() {
		CodeResult result = new CodeResult();

		// Process get result after complied
		String error = FileUtils
				.readFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/errors");
		if (!error.equals("")) {
			log.info(error);
			result.setError(true);
			result.setResultCmd(error);
		} else {
			String sucess = FileUtils
					.readFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/completed")
					.split(END_PROCESS_CHAR)[0];
			if (result != null) {
				log.info(sucess);
				result.setError(false);
				result.setResultCmd(sucess);
			}
		}

		FileUtils.removeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
		return result;
	}

}
