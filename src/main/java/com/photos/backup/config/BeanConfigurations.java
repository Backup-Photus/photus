package com.photos.backup.config;

import com.photos.backup.constants.ConfigurationConstants;
import com.photos.backup.exception.InvalidConfigurationException;
import com.photos.backup.repository.DirRepository;
import com.photos.backup.repository.SystemConfigsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Configuration
public class BeanConfigurations {

    @Value("${microservice.extractor}")
    String extractorMicroservice;
    @Bean
    public SystemConfigsRepository configsRepository() throws InvalidConfigurationException {
       try{
           String dataDir= Objects.requireNonNull(System.
                   getenv(ConfigurationConstants.DATA_DIR_ENV_NAME))
                   .replaceAll("~",System.getProperty("user.home"));
           return new SystemConfigsRepository(dataDir,extractorMicroservice);
       }catch (Exception e){
           throw new InvalidConfigurationException("INVALID_CONFIGURATIONS");
       }
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
