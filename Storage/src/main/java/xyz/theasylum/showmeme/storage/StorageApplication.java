package xyz.theasylum.showmeme.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication()
@EnableDiscoveryClient
public class StorageApplication {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(StorageApplication.class, args);
    }
}
