package server.service;

import org.siqisource.stone.ready.tools.repack.ApiExport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import server.model.ServerComplexModel;

@FeignClient(value = ApplicationConstants.SERVER_NAME)
@ApiExport(ApplicationConstants.SERVER_NAME)
public interface ServerServiceApi {

	@RequestMapping("/sayHello")
	public String sayHello(@RequestParam("name") String name);

	@RequestMapping("/complexModel")
	public ServerComplexModel getComplexModel(@RequestBody ServerComplexModel serverComplexModel);
}
