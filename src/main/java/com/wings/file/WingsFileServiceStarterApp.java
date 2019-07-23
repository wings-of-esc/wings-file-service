package com.wings.file;

import com.wings.file.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class WingsFileServiceStarterApp {
    public static void main(String[] args) {
        SpringApplication.run(WingsFileServiceStarterApp.class, args);
    }
}
