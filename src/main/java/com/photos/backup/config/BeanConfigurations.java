package com.photos.backup.config;

import com.photos.backup.constants.ConfigurationConstants;
import com.photos.backup.repository.SystemConfigsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurations {
    @Bean
    public SystemConfigsRepository configsRepository(){
        String dataDir=System.getenv(ConfigurationConstants.DATA_DIR_ENV_NAME).replaceAll("~",System.getProperty("user.home"));
        return new SystemConfigsRepository(dataDir);
    }
}
