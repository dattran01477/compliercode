package com.itcode.itcodeweb.service.excute;

import com.itcode.itcodeweb.docker.DockerSandboxService;
import com.itcode.itcodeweb.model.respone.CodeResult;

public interface IExecuteService {
	public CodeResult runComplier(DockerSandboxService docker);
}
