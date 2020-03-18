package com.itcode.itcodeweb.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcode.itcodeweb.model.domain.CodeTemplate;
import com.itcode.itcodeweb.repository.CodeTemplateRepository;

@Service
public class TemplateService {

	@Autowired
	CodeTemplateRepository codeTemplateRepository;

	public CodeTemplate findTemplateByName(String name) {
		return codeTemplateRepository.findByName(name);
	}
}
