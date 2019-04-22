package server.model;

import java.util.List;
import java.util.Map;

public class ServerComplexModel {

	private String name;

	private Map<String, SubModelInMap> subModelMap;

	private List<SubModelInList> subModelInList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, SubModelInMap> getSubModelMap() {
		return subModelMap;
	}

	public void setSubModelMap(Map<String, SubModelInMap> subModelMap) {
		this.subModelMap = subModelMap;
	}

	public List<SubModelInList> getSubModelInList() {
		return subModelInList;
	}

	public void setSubModelInList(List<SubModelInList> subModelInList) {
		this.subModelInList = subModelInList;
	}

}
