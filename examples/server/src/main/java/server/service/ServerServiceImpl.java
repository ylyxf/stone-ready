package server.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.model.ServerComplexModel;

@RestController
public class ServerServiceImpl implements ServerServiceApi {

	@Override
	@RequestMapping("/sayHello")
	public String sayHello(@RequestParam("name") String name) {
		return "hello," + name;
	}

	@Override
	@RequestMapping("/complexModel")
	public ServerComplexModel getComplexModel(@RequestBody ServerComplexModel serverComplexModel) {
		serverComplexModel.setName("I change it :" + serverComplexModel.getName());
		serverComplexModel.getSubModelInList().get(0).setValue("￥%#&@*");
		serverComplexModel.getSubModelMap().get("4").setValue("汉字乱码不？");
		;
		return serverComplexModel;
	}

	@RequestMapping("/restTemplateHello")
	public String restTemplateHello() {
		return "hello,I am service for restTemplate ";
	}

}
