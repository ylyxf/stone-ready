package server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({ "server", "org.siqisource.stone.ready" })
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Bean
	@org.springframework.cloud.context.config.annotation.RefreshScope
	public ApplicationInfoManager eurekaApplicationInfoManager(EurekaInstanceConfig config) {
		InstanceInfo instanceInfo = new InstanceInfoFactory().create(config);
		
		InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder();
		builder.setIPAddr("192.168.0.11");
		builder.setPort(123);
		InstanceInfo newInstanceInfo = builder.getRawInstance();
		return new ApplicationInfoManager(config, newInstanceInfo);
	}

}