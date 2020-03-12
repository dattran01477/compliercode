package com.itcode.itcodeweb.docker;

import java.io.File;
import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author thanhdat
 *
 */
@Service
@Slf4j
public class DockerSandboxService extends AbstractDockerSandBoxService {

	private static int time = 0;
	private static final String END_PROCESS_CHAR = "-COMPILEBOX::ENDOFOUTPUT-";

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
			FileUtils.writeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/"
					+ this.dockerSandboxModel.getFileName(), this.dockerSandboxModel.getCode());
			log.info(this.dockerSandboxModel.getLangName() + " file was saved!");
			Runtime.getRuntime().exec("chmod 777 \"" + this.dockerSandboxModel.getPath()
					+ this.dockerSandboxModel.getFolder() + "/" + this.dockerSandboxModel.getFileName() + "\"");

			// step 3
			FileUtils.writeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/inputFile",
					this.dockerSandboxModel.getStdinData());
			log.info("Input file was saved!");
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	public String execute() {
		String st = this.dockerSandboxModel.getPath() + "DockerTimeout.sh " + this.dockerSandboxModel.getTimeOutValue()
				+ "s -u mysql -e \'NODE_PATH=/usr/local/lib/node_modules\' -i -t -v  "
				+ this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + ":/usercode "
				+ this.dockerSandboxModel.getVmName() + " /usercode/script.sh "
				+ this.dockerSandboxModel.getComplierName() + " " + this.dockerSandboxModel.getFileName() + " "
				+ this.dockerSandboxModel.getOutPutCommand() + " " + this.dockerSandboxModel.getExtraArguments();
		log.info(st);
		try {
			Runtime.getRuntime().exec(st);
			this.enabledSchedule.set(true);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Scheduled(fixedRate = 1000)
	public void scanFileSuccess() throws Exception {
		if (enabledSchedule.get()) {
			if (time > 10) {
				this.enabledSchedule.set(false);
			} else {

				String error = FileUtils
						.readFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/errors");
				if (!error.equals("")) {
					//FileUtils.removeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
					this.enabledSchedule.set(false);
					log.info(error);
				} else {
					String result = FileUtils.readFile(
							this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder() + "/completed")
							.split(END_PROCESS_CHAR)[0];
					if (result != null) {
						//FileUtils.removeFile(this.dockerSandboxModel.getPath() + this.dockerSandboxModel.getFolder());
						this.enabledSchedule.set(false);
						log.info(result);
					} else {
						time++;
						log.info("processing");
					}
				}
			}
		}
	}

}
