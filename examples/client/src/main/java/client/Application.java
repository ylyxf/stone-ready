package client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import okhttp3.OkHttpClient;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({ "client", "server", "org.siqisource.stone.ready" })
@EnableFeignClients(basePackages = "server")
@Configuration
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(OkHttpClient client) {
		OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);
		return new RestTemplate(okHttp3ClientHttpRequestFactory);
	}

	@Bean
	public OkHttpClient.Builder okHttpClientBuilder() {
		return new OkHttpClient.Builder().addInterceptor(new OkHttpLoggingInterceptor());
	}

}