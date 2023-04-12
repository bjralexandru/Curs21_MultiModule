package bowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class BowserZullProxy {
    public static void main(String[] args) {
        SpringApplication.run(BowserZullProxy.class, args);
    }
}

