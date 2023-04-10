package bjr.bowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"bjr"})
@EntityScan(basePackages = {"bjr"})
//@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = {"bjr"})
@ConfigurationPropertiesScan(basePackages = {"bjr"})
public class PaymentApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApp.class, args);
    }
}
