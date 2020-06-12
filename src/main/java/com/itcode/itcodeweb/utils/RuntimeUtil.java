package com.itcode.itcodeweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeUtil {

	public static void showExcuteResult(Process proc) {

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		// Read the output from the command
		log.info("the standard output of the command:\n");
		String s = null;
		try {
			while ((s = stdInput.readLine()) != null) {
				log.info(s);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		
		int exitCode;
		try {
			exitCode = proc.waitFor();
			log.info("\nExited with error code : " + exitCode);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}

		log.info("the standard error of the command:\n");
		try {
			while ((s = stdError.readLine()) != null) {
				log.error(s);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
