package apalala.exploros.rest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.configuration.Configuration;

import apalala.exploros.utils.ApplicationConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/test")
public class ServeConfiguration {

	@Inject
	@ApplicationConfiguration(name = "storage.mysql")
	Configuration dbConf;
	
	@POST
	@Path("/json")
    @Produces({ "application/json" })
    public String getConfJSON() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(dbConf);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
    }
}
