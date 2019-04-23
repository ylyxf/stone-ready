package client.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import client.service.ClientService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import server.model.ServerComplexModel;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	RestTemplate restTemplate;
	
//	@Autowired
//	OkHttpClient client;

	@RequestMapping("/hello")
	public String doAppendHello(@RequestParam("name") String name) throws IOException {
//		Request request = new Request.Builder().url("http://www.baidu.com").build();
//	    Response response = client.newCall(request).execute();
//	    if (response.isSuccessful()) {
//	        return response.body().string();
//	    } else {
//	        throw new IOException("Unexpected code " + response);
//	    }
		return this.clientService.appendHello(name);
	}

	@RequestMapping("/model")
	public ServerComplexModel doComplexModel() {
		return this.clientService.getComplexModel();
	}

	@RequestMapping("/restTemplateHello")
	public String restTemplateHello() {
		return "Hi," + restTemplate.getForEntity("http://EXAMPLE-SERVER/restTemplateHello", String.class).getBody();
	}

}
