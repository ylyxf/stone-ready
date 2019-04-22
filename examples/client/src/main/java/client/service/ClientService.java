package client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.model.ServerComplexModel;
import server.model.SubModelInList;
import server.model.SubModelInMap;
import server.service.ServerServiceApi;

@Service
public class ClientService {

	@Autowired
	ServerServiceApi serverServiceApi;

	public String appendHello(String name) {
		String result = serverServiceApi.sayHello(name);
		return result;
	}

	public ServerComplexModel getComplexModel() {
		ServerComplexModel serverComplexModel = new ServerComplexModel();
		serverComplexModel.setName("Joy");

		List<SubModelInList> subModelInList = new ArrayList<SubModelInList>();

		SubModelInList one = new SubModelInList();
		one.setCode("In SubModel List Code one");
		one.setValue("I am one in List");
		subModelInList.add(one);

		SubModelInList two = new SubModelInList();
		two.setCode("In SubModel List Code 2");
		two.setValue("I am 2 in List");
		subModelInList.add(two);

		serverComplexModel.setSubModelInList(subModelInList);

		Map<String, SubModelInMap> subModelMap = new HashMap<String, SubModelInMap>();

		SubModelInMap three = new SubModelInMap();
		three.setCode("code 3 in map");
		three.setValue("value 3 in map");
		subModelMap.put("3--->", three);

		SubModelInMap four = new SubModelInMap();
		four.setCode("4");
		four.setValue("value 4 in map");
		subModelMap.put("4", four);

		serverComplexModel.setSubModelMap(subModelMap);

		serverComplexModel = serverServiceApi.getComplexModel(serverComplexModel);
		return serverComplexModel;
	}

}
