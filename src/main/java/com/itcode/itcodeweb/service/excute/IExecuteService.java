package com.itcode.itcodeweb.service.excute;

import com.itcode.itcodeweb.model.app.CodeSubmit;
import com.itcode.itcodeweb.model.respone.CodeResult;
import com.itcode.itcodeweb.service.docker.DockerSandboxService;

public interface IExecuteService<C extends CodeSubmit> {
	public CodeResult runComplier(DockerSandboxService docker, C code);
}
