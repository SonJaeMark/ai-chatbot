package com.github.sonjaemark.ai_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class AiChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiChatbotApplication.class, args);
	}

}
