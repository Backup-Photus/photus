package com.photos.backup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackupApplication {
	enum FileOpConstants{
		NO_FREE_SPACE
	}
	public static void main(String[] args) {
		SpringApplication.run(BackupApplication.class, args);
	}

}


