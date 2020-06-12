package com.itcode.itcodeweb.service.docker;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

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

	private static final String ERROR_FILE_NAME = "errors";

	private static final String SUSSCESS_FILE_NAME = "completed";

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
		String stLogCommand = this.dockerSandboxModel.getPath() + "DockerTimeout.sh "
				+ this.dockerSandboxModel.getTimeOutValue()
				+ "s -u root -e \'NODE_PATH=/usr/local/lib/node_modules\' -i -t -v  "
				+ this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + ":/usercode "
				+ this.dockerSandboxModel.getVmName() + " /usercode/script.sh " + "\""
				+ this.dockerSandboxModel.getComplierName() + "\"" + " " + this.dockerSandboxModel.getFileName() + " "
				+ this.dockerSandboxModel.getOutPutCommand() + " " + this.dockerSandboxModel.getExtraArguments();

		String[] stArray = { this.dockerSandboxModel.getPath() + "DockerTimeout.sh",
				this.dockerSandboxModel.getTimeOutValue() + "s", "-u", "root", "-e",
				"NODE_PATH=/usr/local/lib/node_modules", "-i", "-t", "-v",
				this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + ":/usercode",
				this.dockerSandboxModel.getVmName(), "/usercode/script.sh", this.dockerSandboxModel.getComplierName(),
				this.dockerSandboxModel.getFileName(), this.dockerSandboxModel.getOutPutCommand(),
				this.dockerSandboxModel.getExtraArguments() };

		log.info(stLogCommand);
		try {
			log.info("Start time excute: " + LocalDateTime.now().getSecond());
			Runtime.getRuntime().exec(stArray).waitFor();
			log.info("End time excute: " + LocalDateTime.now().getSecond());
			return scanFileSuccess();
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
		}
		return new CodeResult();
	}

	public CodeResult scanFileSuccess() {
		CodeResult result = new CodeResult();

		// Process error
		String error = FileUtils.readFile(
				this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/" + ERROR_FILE_NAME);
		if (!error.isEmpty()) {
			log.info("error: "+error);
			result.getErrorMessage().setErrorComplieMessage(error);
		}

		// Process sucess
		String sucess = FileUtils.readFile(
				this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/" + SUSSCESS_FILE_NAME)
				.split(END_PROCESS_CHAR)[0];
		if (!sucess.isEmpty()) {
			log.info("sucess: "+sucess);
			result.getSuccessMessage().setSuccessComplieMessage(sucess);
		}

		//FileUtils.removeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
		return result;
	}

}
