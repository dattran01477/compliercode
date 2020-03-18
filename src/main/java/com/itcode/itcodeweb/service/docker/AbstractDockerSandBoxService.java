package com.itcode.itcodeweb.service.docker;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.itcode.itcodeweb.model.docker.DockerSandboxModel;
import com.itcode.itcodeweb.model.respone.CodeResult;

/**
 * @author thanhdat
 *
 */
public abstract class AbstractDockerSandBoxService {
	
	protected AtomicBoolean enabledSchedule = new AtomicBoolean(false);
	 
	protected AtomicInteger batchRunCounter = new AtomicInteger(0);

	protected DockerSandboxModel dockerSandboxModel;

	protected Process process;

	public CodeResult run(DockerSandboxModel dockerInfo) {
		this.dockerSandboxModel = dockerInfo;
		prepare();
		return execute();
	}

	public abstract void prepare();

	public abstract CodeResult execute();
}
