package vk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"vk", "user"})
@EnableJpaRepositories({"vk", "user"})
@EntityScan({"vk", "user"})
public class VkApplication {
    public static void main(String... args) {
        SpringApplication.run(VkApplication.class, args);
    }
}