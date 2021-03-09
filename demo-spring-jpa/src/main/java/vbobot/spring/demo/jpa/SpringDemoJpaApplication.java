package vbobot.spring.demo.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "vbobot.spring.demo.jpa")
public class SpringDemoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoJpaApplication.class, args);
    }
}
