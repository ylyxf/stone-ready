package client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({ "client", "server","org.siqisource.stone.ready" })
@EnableFeignClients(basePackages = "server")
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}