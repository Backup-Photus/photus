package com.photos.backup.config;

import com.photos.backup.constants.ConfigurationConstants;
import com.photos.backup.repository.DirRepository;
import com.photos.backup.repository.SystemConfigsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Configuration
public class BeanConfigurations {

    @Value("${application.port}")
    private String port;

    @Value("${application.baseurl}")
    private String baseurl;
    @Bean
    public SystemConfigsRepository configsRepository(Environment environment){
        String dataDir= Objects.requireNonNull(environment.getProperty(ConfigurationConstants.DATA_DIR_ENV_NAME)).replaceAll("~",System.getProperty("user.home"));
        return new SystemConfigsRepository(dataDir,port,baseurl);
    }
    @Bean
    DirRepository configsDirRepository(SystemConfigsRepository systemConfigsRepository){
        return new DirRepository(systemConfigsRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
