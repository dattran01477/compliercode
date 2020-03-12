package com.itcode.itcodeweb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.itcode.itcodeweb")
public class ScheduleConfig {

}
