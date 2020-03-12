package com.itcode.itcodeweb.model.docker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thanhdat
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class DockerSandboxModel {
	private Long timeOutValue;
	
	private String path;
	
	private String folder;
	
	private String vmName;
	
	private String complierName;
	
	private String fileName;
	
	private String code;
	
	private String outPutCommand;
	
	private String langName;
	
	private String extraArguments;
	
	private String stdinData;
}
