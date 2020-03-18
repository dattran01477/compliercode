package com.itcode.itcodeweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.itcode.itcodeweb.model.domain.CodeTemplate;

public interface CodeTemplateRepository extends MongoRepository<CodeTemplate, String> {

	CodeTemplate findByName(String name);
}
