package com.itcode.itcodeweb.service.excute;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;

public interface IExecuteService {
	public CodeResult runComplier(DockerSandboxService docker, CodeSubmit code);
}
