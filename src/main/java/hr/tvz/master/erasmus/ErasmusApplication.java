package hr.tvz.master.erasmus;

import hr.tvz.master.erasmus.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ErasmusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErasmusApplication.class, args);
    }
}
