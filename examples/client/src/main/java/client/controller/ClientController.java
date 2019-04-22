package client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import client.service.ClientService;
import server.model.ServerComplexModel;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/hello")
	public String doAppendHello(@RequestParam("name") String name) {
		return this.clientService.appendHello(name);
	}

	@RequestMapping("/model")
	public ServerComplexModel doComplexModel() {
		return this.clientService.getComplexModel();
	}

	@RequestMapping("/restTemplateHello")
	public String restTemplateHello() {
		return "Hi," + restTemplate.getForEntity("http://EXAMPLE-SERVER2/restTemplateHello", String.class).getBody();
	}

}
